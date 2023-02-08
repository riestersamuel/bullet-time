package de.hdmstuttgart.bulletjournalapp.TimerPackage;

import android.os.CountDownTimer;

public class TimerHolder {
	private static TimerHolder instance;
	private CountDownTimer timer;
	private CountDownTimer longBreakTimer;
	private CountDownTimer shortBreakTimer;

	private TimerHolder() {
	}

	public static TimerHolder getInstance() {
		if (instance == null) {
			instance = new TimerHolder();
		}
		return instance;
	}

	public CountDownTimer getTimer() {
		return timer;
	}

	public void setTimer(CountDownTimer timer) {
		this.timer = timer;
	}

	public CountDownTimer getLongBreakTimer() {
		return longBreakTimer;
	}

	public void setLongBreakTimer(CountDownTimer longBreakTimer) {
		this.longBreakTimer = longBreakTimer;
	}

	public CountDownTimer getShortBreakTimer() {
		return shortBreakTimer;
	}

	public void setShortBreakTimer(CountDownTimer shortBreakTimer) {
		this.shortBreakTimer = shortBreakTimer;
	}

	public void cancelTimer() {
		if (timer != null) {
			timer.cancel();
		}
	}

	public void cancelLongBreakTimer() {
		if (longBreakTimer != null) {
			longBreakTimer.cancel();
		}
	}

	public void cancelShortBreakTimer() {
		if (shortBreakTimer != null) {
			shortBreakTimer.cancel();
		}
	}

	public void startTimer() {
		if (timer != null) {
			timer.start();
		}
	}

	public void startLongBreakTimer() {
		if (longBreakTimer != null) {
			longBreakTimer.start();
		}
	}

	public void startShortBreakTimer() {
		if (shortBreakTimer != null) {
			shortBreakTimer.start();
		}
	}
}

