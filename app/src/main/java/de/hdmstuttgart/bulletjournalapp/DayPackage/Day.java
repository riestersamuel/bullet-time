package de.hdmstuttgart.bulletjournalapp.DayPackage;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.bulletjournalapp.BulletsPackage.Bullet;
import de.hdmstuttgart.bulletjournalapp.NotePackage.Note;

@Entity
public class Day {

	@PrimaryKey
	public long date;

	@Embedded
	public ArrayList<Bullet> bullets;

	public Day(long date, ArrayList<Bullet> bullets) {
		this.date = date;
		this.bullets = bullets;
	}

}
