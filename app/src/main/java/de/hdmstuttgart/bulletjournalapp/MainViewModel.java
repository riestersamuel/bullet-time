package de.hdmstuttgart.bulletjournalapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.hdmstuttgart.bulletjournalapp.DayPackage.Day;
import de.hdmstuttgart.bulletjournalapp.DayPackage.DayRepository;
import de.hdmstuttgart.bulletjournalapp.NotePackage.Note;
import de.hdmstuttgart.bulletjournalapp.NotePackage.NoteRepository;

public class MainViewModel extends AndroidViewModel {

	private final DayRepository dayRepository;
	private final NoteRepository noteRepository;
	private final LiveData<List<Note>> allNotes;

	public MainViewModel(@NonNull Application application, DayRepository dayRepository) {
		super(application);
		this.noteRepository = new NoteRepository(application);
		this.dayRepository = dayRepository;
		allNotes = noteRepository.getAll();
	}

	public LiveData<List<Note>>  getAllNotes() {
		return noteRepository.getAll();
	}
	public LiveData<List<Note>>  getNotesByKeyword(String keyword) {
		return noteRepository.getNotesByKeyword(keyword);
	}

	public void insertNote(Note note) {
		noteRepository.insert(note);
	}
	public void deleteNote(Note note) {
		noteRepository.delete(note);
	}
	public void updateNote(Note note) {
		noteRepository.update(note);
	}

	public void insertNewDay(Day day) {
		dayRepository.insertNewDay(day);
	}
	public void getDay(String date) {
		dayRepository.getDay(date);
	}

}
