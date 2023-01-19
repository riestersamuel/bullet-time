package de.hdmstuttgart.bulletjournalapp.DayPackage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface DayDao {
    @Insert
    void insert(Day day);

    @Update
    void update(Day day);

    @Delete
    void delete(Day day);

    @Query("SELECT * FROM day WHERE date = :date")
    Day getByDate(long date);

    @Query("SELECT * FROM day")
    List<Day> getAll();
}