package de.hdmstuttgart.bulletjournalapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface NoteDao {

	@Query("SELECT * FROM note")
	LiveData<List<Note>> getAll();

	@Insert
	void insert(Note note);

	@Delete
	void delete(Note note);

	@Query("SELECT * FROM note WHERE title LIKE :keyword")
	LiveData<List<Note>> getMoviesByKeyword(String keyword);
}
