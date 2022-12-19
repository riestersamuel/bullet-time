package de.hdmstuttgart.bulletjournalapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

	private final NoteRepository noteRepository;
	private final LiveData<List<Note>> allNotes;

	public MainViewModel(@NonNull Application application) {
		super(application);
		this.noteRepository = new NoteRepository(application);
		allNotes = noteRepository.getAll();
	}

	public static List<Note> getAllNotes() {
		return getAllNotes();
	}

	public void insert(Note note) {
		noteRepository.insert(note);
	}

	public void delete(Note note) {
		noteRepository.delete(note);
	}
}
