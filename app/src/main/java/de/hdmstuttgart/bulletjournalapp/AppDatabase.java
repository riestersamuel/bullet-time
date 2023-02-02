package de.hdmstuttgart.bulletjournalapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.hdmstuttgart.bulletjournalapp.BulletsPackage.Bullet;
import de.hdmstuttgart.bulletjournalapp.DayPackage.Converters;
import de.hdmstuttgart.bulletjournalapp.DayPackage.Day;
import de.hdmstuttgart.bulletjournalapp.DayPackage.DayDao;
import de.hdmstuttgart.bulletjournalapp.NotePackage.Note;
import de.hdmstuttgart.bulletjournalapp.NotePackage.NoteDao;

@Database(entities = {Note.class, Day.class},  version = 2)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
	public abstract NoteDao noteDao();
	public abstract DayDao dayDao();

	private static volatile AppDatabase INSTANCE;

	private static final int NUMBER_OF_THREADS = 4;
	public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

	public static AppDatabase getDatabase(final Context context) {
		if (INSTANCE == null) {
			synchronized (AppDatabase.class) {
				if (INSTANCE == null) {
					// Create database here
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
									AppDatabase.class, "word_database")
							.fallbackToDestructiveMigration()
							.allowMainThreadQueries()
							.build();
				}
			}
		}
		return INSTANCE;
	}

}
