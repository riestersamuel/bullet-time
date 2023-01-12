package de.hdmstuttgart.bulletjournalapp.TimerPackage;

import android.os.CountDownTimer;

import java.util.TimerTask;

public class Timer {

	/* Maiks vorheriger Code
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
	 */

	// Samuels neuer Code
	// Timer duration in milliseconds, here 25 minutes
	long duration = 1500000;

	// Interval between each tick in milliseconds, here 1 minute
	long interval = 60000;

	// Create the timer
	CountDownTimer timer = new CountDownTimer(duration, interval) {
		@Override
		public void onTick(long millisUntilFinished) {
			// Update the UI with the remaining time

		}

		@Override
		public void onFinish() {
			// Do something when the timer finishes
		}
	};

	// Starting the timer
	public void startTimer() {
		timer.start();
	}
}
