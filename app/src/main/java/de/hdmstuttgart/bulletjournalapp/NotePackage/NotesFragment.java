package de.hdmstuttgart.bulletjournalapp.NotePackage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.bulletjournalapp.MainViewModel;
import de.hdmstuttgart.bulletjournalapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";

	private String Date;
	public LiveData<List<Note>> notes;
	MainViewModel viewModel;
	NoteListAdapter adapter;

	public NotesFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * @param param1 Parameter 1.
	 * @return A new instance of fragment NotesFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static NotesFragment newInstance(String param1) {
		NotesFragment fragment = new NotesFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_notes, container, false);

		// Get a reference to the MaterialToolbar view
		MaterialToolbar topBarTitle = view.findViewById(R.id.topAppBar);

		// Set the title for the MaterialToolbar
		topBarTitle.setTitle("Notes");

		// Inflate the layout for this fragment
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		MaterialToolbar toolbar = (MaterialToolbar) view.findViewById(R.id.topAppBar);


		toolbar.setOnMenuItemClickListener(item -> {
			if (item.getItemId() == R.id.searchBut) {
				SearchView searchView = requireView().findViewById(R.id.searchBarNotes);
				searchView.setVisibility(View.VISIBLE);
				searchView.callOnClick();
				searchView.requestFocus();
				return true;
			}
			return false;
		});

		toolbar.setOnMenuItemClickListener(item -> {
			if(item.getItemId() == R.id.helpBut) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle(R.string.information_title);
				builder.setMessage(R.string.information_text);
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.show();
			}
			return false;
		});

		// Get the ViewModel
		viewModel = new ViewModelProvider(this).get(MainViewModel.class);
		if (getArguments() != null) {
			Date = getArguments().getString(ARG_PARAM1);
		}

		RecyclerView recyclerView = view.findViewById(R.id.RecyclerViewNotes);
		recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
		recyclerView.setHasFixedSize(true);


		adapter = new NoteListAdapter(new ArrayList<>(), (note, position) -> {}, (note, position) -> {});

		// set search view listener to filter notes
		SearchView searchView = view.findViewById(R.id.searchBarNotes);
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				viewModel.getNotesByKeyword(newText).observe(getViewLifecycleOwner(), notes -> {
					recyclerView.setAdapter(new NoteListAdapter(notes, (note, position) -> {
						FragmentManager fragmentManager = getParentFragmentManager();
						NewNoteFragment newNoteFragment = NewNoteFragment.newInstance(note.getTitle(),note.getContent(), note);
						fragmentManager.beginTransaction().replace(R.id.fragment_container, newNoteFragment).commit();
					}, (note, position) -> {
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
						builder.setTitle("Delete Note");
						builder.setMessage("Are you sure you want to delete this note?");
						builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								viewModel.deleteNote(note);
							}
						});
						builder.setNegativeButton("No", null);
						builder.show();
					}));
				});
				return true;
			}
		});
		searchView.setOnCloseListener(() -> {
			searchView.setVisibility(View.GONE);
			return false;
		});

		// Get all notes from the database and display them in the recyclerview
		viewModel.getAllNotes().observe(getViewLifecycleOwner(), notes -> {
			if(notes == null) return;
			adapter = new NoteListAdapter(notes, (note, position) -> {
				FragmentManager fragmentManager = getParentFragmentManager();
				NewNoteFragment newNoteFragment = NewNoteFragment.newInstance(note.getTitle(),note.getContent(), note);
				fragmentManager.beginTransaction().replace(R.id.fragment_container, newNoteFragment).commit();

			}, (note, position) -> {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Delete Note");
				builder.setMessage("Are you sure you want to delete this note?");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						viewModel.deleteNote(note);
					}
				});
				builder.setNegativeButton("No", null);
				builder.show();
			});
			recyclerView.setAdapter(adapter);
		});

		notes = viewModel.getAllNotes();

		//set setOnItemClickListener for the FAB to open the NewNoteFragment
		view.findViewById(R.id.extended_fab_note).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getParentFragmentManager();
				NewNoteFragment newNoteFragment = NewNoteFragment.newInstance(null,null, null);
				fragmentManager.beginTransaction().replace(R.id.fragment_container, newNoteFragment).commit();
			}
		});
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.notes_top_app_bar, menu);
	}


}

