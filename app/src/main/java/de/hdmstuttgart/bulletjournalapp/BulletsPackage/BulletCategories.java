package de.hdmstuttgart.bulletjournalapp.BulletsPackage;

import de.hdmstuttgart.bulletjournalapp.R;

public enum BulletCategories {
	TASK,
	DAILY_HIGHLIGHT,
	EVENT,
	NOTE;

	public int getImage() {
		switch (this) {
			case TASK:
				return R.drawable.baseline_check_circle_24;
			case DAILY_HIGHLIGHT:
				return R.drawable.baseline_star_24;
			case EVENT:
				return R.drawable.ic_baseline_event_note_24;
			case NOTE:
				return R.drawable.baseline_notes_24;
		}
		return 0;
	}
}