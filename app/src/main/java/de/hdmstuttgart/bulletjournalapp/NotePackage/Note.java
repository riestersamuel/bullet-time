package de.hdmstuttgart.bulletjournalapp.NotePackage;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity
public class Note {

	@PrimaryKey(autoGenerate = true)
	public int uid;

	private String title;
	private String content;
	private String creationDate;
	private String editDate;

	public Note(String title, String content) {
		this.title = title;
		this.content = content;
		this.creationDate = LocalDateTime.now().toString();
		this.editDate = LocalDateTime.now().toString();
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

	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		 this.creationDate = creationDate;
	}
	public LocalDateTime getCreationDateAsDate() {
		return LocalDateTime.parse(creationDate);
	}

	public String getEditDate() {
		return editDate;
	}
	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}
	public LocalDateTime getEditDateAsDate() {
		return LocalDateTime.parse(editDate);
	}

	public void setEditDate() {
		this.editDate = LocalDateTime.now().toString();
	}
}
