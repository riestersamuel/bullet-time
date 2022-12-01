package de.hdmstuttgart.bulletjournalapp;

import java.time.LocalDateTime;

public class Note {
	private String title;
	private String content;
	private final LocalDateTime creationDate;
	private LocalDateTime editDate;

	public Note(String title, String content) {
		this.title = title;
		this.content = content;
		this.creationDate = LocalDateTime.now();
		this.editDate = LocalDateTime.now();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public LocalDateTime getEditDate() {
		return editDate;
	}

	public void setEditDate() {
		this.editDate = LocalDateTime.now();
	}
}
