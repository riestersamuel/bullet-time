package de.hdmstuttgart.bulletjournalapp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Day {
	private final LocalDateTime date;
	private List<Bullet> bullets;
	private List<Note> notes;

	public Day(LocalDateTime date) {
		this.date = date;
		bullets = new ArrayList<>();
		notes = new ArrayList<>();
	}

	public void addBullet(Bullet bullet) {
		bullets.add(bullet);
	}

	public void addNote (Note note) {
		notes.add(note);
	}
}
