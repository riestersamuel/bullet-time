package de.hdmstuttgart.bulletjournalapp.BulletsPackage;

public class Bullet {

	private String content;
	private int id;
	private BulletCategories category;

	private boolean isChecked;

	public Bullet(String content, int id, BulletCategories category, boolean isChecked) {
		this.content = content;
		this.id = id;
		this.category = category;
		this.isChecked = isChecked;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String name) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BulletCategories getCategory() {
		return category;
	}

	public void setCategory(BulletCategories category) {
		this.category = category;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}
}
