package de.hdmstuttgart.bulletjournalapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";

	private String Date;
	public List<Note> notes;

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
		if (getArguments() != null) {
			Date = getArguments().getString(ARG_PARAM1);
		}
		notes = MainViewModel.getAllNotes();
		getActivity().findViewById(R.id.extended_fab_note).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Note note = new Note("New Note", "New Note");
				notes.add(note);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_notes, container, false);
	}

	private void loadNotes() {
		//Todo: load notes from database with note_preview layout
	}


}