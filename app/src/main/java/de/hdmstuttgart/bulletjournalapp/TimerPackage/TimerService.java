package de.hdmstuttgart.bulletjournalapp.TimerPackage;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import de.hdmstuttgart.bulletjournalapp.R;
import de.hdmstuttgart.bulletjournalapp.TimerPackage.TimerHolder;

public class TimerService extends Service {

	private CountDownTimer timer;

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	// This method is called when the service is started
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		this.timer = TimerHolder.getInstance().getTimer();
		timer.start();
		// Create notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
				.setSmallIcon(R.drawable.ic_baseline_timer_24)
				.setContentTitle("Timer is running")
				.setContentText("The timer is still running even if you close the app.")
				.setPriority(NotificationCompat.PRIORITY_HIGH);
		startForeground(1, builder.build());
		return super.onStartCommand(intent, flags, startId);
	}

	// This method is called when the service is stopped
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
