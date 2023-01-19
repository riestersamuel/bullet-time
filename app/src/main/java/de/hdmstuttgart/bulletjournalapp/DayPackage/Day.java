package de.hdmstuttgart.bulletjournalapp.DayPackage;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.hdmstuttgart.bulletjournalapp.BulletsPackage.Bullet;
import de.hdmstuttgart.bulletjournalapp.NotePackage.Note;

@Entity
public class Day {
	@PrimaryKey
	public LocalDateTime date;

	@Embedded
	public List<Bullet> bullets;

	public Day(LocalDateTime date, List<Bullet> bullets) {
		this.date = date;
		this.bullets = bullets;
	}
}
