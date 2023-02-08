package de.hdmstuttgart.bulletjournalapp.NotePackage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.File;
import java.io.FileOutputStream;

import de.hdmstuttgart.bulletjournalapp.MainViewModel;
import de.hdmstuttgart.bulletjournalapp.R;

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
	boolean isSave = false;
	MainViewModel viewModel;

	String imageUri;

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
		//fragment.uri_image = note.getUri_Image();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		if (note != null) {
			isEdit = true;
		}
		viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_new_note, container, false);

		// Get a reference to the MaterialToolbar view
		MaterialToolbar topBarTitle = view.findViewById(R.id.topAppBar);

		// Set the title for the MaterialToolbar
		if(isEdit)
			topBarTitle.setTitle("Edit Note");
		else
			topBarTitle.setTitle("New Note");
		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		MaterialToolbar toolbar = view.findViewById(R.id.topAppBar);
		//sets the title and the content of the note
		if (isEdit) {
			((TextView) view.findViewById(R.id.ET_Title)).setText(mParam1);
			((TextView) view.findViewById(R.id.ET_Content)).setText(mParam2);
			String uri = note.getUriImage();
			if(uri != null) {
				addImage2Note(Uri.parse(uri));
			}
		}



		toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String title = ((EditText) view.findViewById(R.id.ET_Title)).getText().toString();
				String content = ((EditText) view.findViewById(R.id.ET_Content)).getText().toString();
				update_Note(title, content);
				FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.fragment_container, new NotesFragment()).commit();
			}
		});

		toolbar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.addImageBut:
						Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
						startActivityForResult(intent, 100);
						return true;
					default:
						return false;
				}
			}
		});
	}



	@Override
	public void onDestroyView() {
		String title = ((EditText) requireView().findViewById(R.id.ET_Title)).getText().toString();
		String content = ((EditText) requireView().findViewById(R.id.ET_Content)).getText().toString();
		update_Note(title, content);
		super.onDestroyView();
	}

	private String saveImageToInternalStorage(Uri imageUri) {
		try {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
			ContextWrapper cw = new ContextWrapper(requireContext().getApplicationContext());
			File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
			File myPath = new File(directory, Math.random() + ".png");
			FileOutputStream fos = new FileOutputStream(myPath);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
			return myPath.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private void update_Note(String title, String content){
		if (isEdit && (!content.equals(mParam2) || !title.equals(mParam1) || imageUri != null) && !isSave) {
			if(!title.equals("") && !content.equals("")) {
				note.setTitle(title);
				note.setContent(content);
				note.setUriImage(imageUri!=null? String.valueOf(imageUri) :"");
				viewModel.updateNote(note);
				isSave = true;
			}
		} else if(!isEdit && !(content.isEmpty() && title.isEmpty()) && !isSave) {
			Note note = new Note(title, content, String.valueOf(imageUri));
			viewModel.insertNote(note);
			isSave = true;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 100 && data != null) {
			imageUri = saveImageToInternalStorage(data.getData());
			addImage2Note(Uri.parse(imageUri));
		}
	}

	private void addImage2Note(Uri uri) {
		requestPermissions(new String[]{Manifest.permission.ACCESS_MEDIA_LOCATION}, 0);
		LinearLayout layout = requireView().findViewById(R.id.linear_layout_images);
		ImageView imageView = new ImageView(requireContext());
		imageView.setId(View.generateViewId());
		try {
			imageView.setImageURI(uri);
		} catch (Exception e) {
			// handle the exception
		}
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
		);
		params.gravity = Gravity.CENTER_HORIZONTAL;
		imageView.setLayoutParams(params);
		layout.addView(imageView);
	}

	private Bitmap resizeImage(Uri uri){
		Bitmap originalBitmap = BitmapFactory.decodeFile(uri.getPath());
		return Bitmap.createScaledBitmap(originalBitmap, originalBitmap.getWidth()*2, originalBitmap.getHeight()*2, false);
	}

}