package de.hdmstuttgart.bulletjournalapp.NotePackage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.hdmstuttgart.bulletjournalapp.AppDatabase;

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

	public void update(Note note) {
		AppDatabase.databaseWriteExecutor.execute(() -> noteDao.update(note.uid, note.getTitle(),note.getContent()));
	}

	public LiveData<List<Note>> getNotesByKeyword(String keyword) {
		return noteDao.getNotesByKeyword(keyword);
	}
}
