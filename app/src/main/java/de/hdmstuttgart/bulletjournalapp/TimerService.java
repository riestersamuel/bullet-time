package de.hdmstuttgart.bulletjournalapp;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.security.Provider;
import java.util.List;
import java.util.Map;

public class TimerService extends Service {

	private CountDownTimer timer;

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		this.timer = TimerHolder.getInstance().getTimer();
		timer.start();
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
				.setSmallIcon(R.drawable.ic_baseline_timer_24)
				.setContentTitle("Timer is running")
				.setContentText("The timer is still running even if you close the app")
				.setPriority(NotificationCompat.PRIORITY_HIGH);
		startForeground(1, builder.build());
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopForeground(true);
		//timer.cancel();
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

}
