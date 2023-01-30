package de.hdmstuttgart.bulletjournalapp;

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
}

