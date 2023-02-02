package de.hdmstuttgart.bulletjournalapp.DayPackage;

import android.app.Application;

import java.util.ArrayList;

import de.hdmstuttgart.bulletjournalapp.AppDatabase;
import de.hdmstuttgart.bulletjournalapp.NotePackage.NoteDao;

public class DayRepository {

    private final DayDao dayDao;

    public DayRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        dayDao = db.dayDao();
    }

    public void insertNewDay(Day day) {
        AppDatabase.databaseWriteExecutor.execute(() -> dayDao.insert(day));
    }

    public Day getDay(String date) {
        return dayDao.getByDate(date);
    }

    public void updateDay(Day day) {
        AppDatabase.databaseWriteExecutor.execute(() -> dayDao.update(day));
    }
}
