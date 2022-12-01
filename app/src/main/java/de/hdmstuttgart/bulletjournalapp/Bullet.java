package de.hdmstuttgart.bulletjournalapp;

import java.time.LocalDateTime;

public class Bullet {

	private String name;
	private int id;
	private int priority;
	private BulletCategories category;
	private final LocalDateTime Date;

	public Bullet(String name, int id, int priority, BulletCategories category) {
		this.name = name;
		this.id = id;
		this.priority = priority;
		this.category = category;
		Date = LocalDateTime.now();
	}


	public LocalDateTime getDate() {
		return Date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public BulletCategories getCategory() {
		return category;
	}

	public void setCategory(BulletCategories category) {
		this.category = category;
	}
}
