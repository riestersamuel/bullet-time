package de.hdmstuttgart.bulletjournalapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.hdmstuttgart.bulletjournalapp.NotePackage.NoteRepository;

public class MainViewModel extends AndroidViewModel {

	private final NoteRepository noteRepository;
	private final LiveData<List<Note>> allNotes;

	public MainViewModel(@NonNull Application application) {
		super(application);
		this.noteRepository = new NoteRepository(application);
		allNotes = noteRepository.getAll();
	}

	public LiveData<List<Note>>  getAllNotes() {
		return noteRepository.getAll();
	}

	public void insert(Note note) {
		noteRepository.insert(note);
	}

	public void delete(Note note) {
		noteRepository.delete(note);
	}
}
