package de.hdmstuttgart.bulletjournalapp;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

	private final NoteDao noteDao;

	public NoteRepository(Application application) {
		AppDatabase db = AppDatabase.getDatabase(application);
		noteDao = db.noteDao();
	}

	public NoteRepository(NoteDao noteDao) {
		this.noteDao = noteDao;
	}


	public LiveData<List<Note>> getAll() {
		return noteDao.getAll();
	}

	public void insert(Note note) {
		AppDatabase.databaseWriteExecutor.execute(() -> noteDao.insert(note));
	}

	public void delete(Note note) {
		AppDatabase.databaseWriteExecutor.execute(() -> noteDao.delete(note));
	}
}
