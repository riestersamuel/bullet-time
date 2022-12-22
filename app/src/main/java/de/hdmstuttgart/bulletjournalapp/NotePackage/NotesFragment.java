package de.hdmstuttgart.bulletjournalapp.NotePackage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.hdmstuttgart.bulletjournalapp.MainActivity;
import de.hdmstuttgart.bulletjournalapp.MainViewModel;
import de.hdmstuttgart.bulletjournalapp.Note;
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
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_notes, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		RecyclerView recyclerView = view.findViewById(R.id.RecyclerViewNotes);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setHasFixedSize(true);

		adapter = new NoteListAdapter(new ArrayList<>(), (note, position) -> {});



		viewModel = new ViewModelProvider(this).get(MainViewModel.class);
		if (getArguments() != null) {
			Date = getArguments().getString(ARG_PARAM1);
		}
		viewModel.getAllNotes().observe(getViewLifecycleOwner(), notes -> {
			if(notes == null) return;
			adapter = new NoteListAdapter(notes, (note, position) -> {
				LayoutInflater inflater = getLayoutInflater();
				View dialogView = inflater.inflate(R.layout.new_note_layout, null);

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setView(dialogView);

				final EditText title = dialogView.findViewById(R.id.ET_Title);
				final EditText content = dialogView.findViewById(R.id.ET_Content);
				title.setText(note.getTitle());
				content.setText(note.getContent());

				builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						note.setTitle(title.getText().toString());
						note.setContent(content.getText().toString());
						viewModel.update(note);
					}
				});
				builder.setNegativeButton("Cancel", null);

				AlertDialog dialog = builder.create();
				dialog.show();
			});
			recyclerView.setAdapter(adapter);
		});

		notes = viewModel.getAllNotes();
		view.findViewById(R.id.extended_fab_note).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = getLayoutInflater();
				View dialogView = inflater.inflate(R.layout.new_note_layout, null);

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setView(dialogView);

				final EditText title = dialogView.findViewById(R.id.ET_Title);
				final EditText content = dialogView.findViewById(R.id.ET_Content);

				builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Note newNote = new Note(title.getText().toString(),content.getText().toString());
						viewModel.insert(newNote);
					}
				});
				builder.setNegativeButton("Cancel", null);

				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
	}

	private void loadNotes() {
		//Todo: load notes from database with note_preview layout
	}


}