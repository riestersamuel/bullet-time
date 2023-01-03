package de.hdmstuttgart.bulletjournalapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import de.hdmstuttgart.bulletjournalapp.NotePackage.NotesFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewNoteFragment extends Fragment {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	private Note note;
	boolean isEdit;

	MainViewModel viewModel;

	public NewNoteFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment NewNoteFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static NewNoteFragment newInstance(String param1, String param2, Note note) {
		NewNoteFragment fragment = new NewNoteFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		fragment.note = note;
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		if(note != null) {
			isEdit = true;
		}
		viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_new_note, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//sets the title and the content of the note
		((TextView) view.findViewById(R.id.ET_Title)).setText(mParam1);
		((TextView) view.findViewById(R.id.ET_Content)).setText(mParam2);

		// save changes or new note
		view.findViewById(R.id.bt_save).setOnClickListener(v -> {
			String title = ((EditText) view.findViewById(R.id.ET_Title)).getText().toString();
			String content = ((EditText) view.findViewById(R.id.ET_Content)).getText().toString();
			if (isEdit) {
				note.setTitle(title);
				note.setContent(content);
				viewModel.updateNote(note);
			} else {
				Note note = new Note(title, content);
				viewModel.insertNote(note);
			}

			FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.fragment_container, new NotesFragment()).commit();
		});


		view.findViewById(R.id.bt_back).setOnClickListener(v -> {
			FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.fragment_container, new NotesFragment()).commit();
		});
	}
}