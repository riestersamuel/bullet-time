package de.hdmstuttgart.bulletjournalapp;

import java.util.TimerTask;

public class Timer {

	private final int short_timer = 25;
	private final int short_break = 5;
	private final int long_break = 15;
	private final int rounds = 4;


	public void startTimer() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {

			}
		};
		java.util.Timer timer = new java.util.Timer("Timer");

		timer.schedule(task, short_timer * 1000);

		//ToDo: for loop for rounds -> short break -> 4 rounds -> long break
	}
}
