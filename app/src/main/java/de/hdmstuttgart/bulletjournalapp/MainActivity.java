package de.hdmstuttgart.bulletjournalapp;

import static com.google.android.material.internal.ViewUtils.dpToPx;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdmstuttgart.bulletjournalapp.DayPackage.DayFragment;
import de.hdmstuttgart.bulletjournalapp.NotePackage.NotesFragment;
import de.hdmstuttgart.bulletjournalapp.TimerPackage.TimerFragment;

public class MainActivity extends AppCompatActivity {

	DayFragment dayFragment = new DayFragment();
	NotesFragment notesFragment = new NotesFragment();
	TimerFragment timerFragment = new TimerFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create Notification Channel for Timer Service
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel("channel_id", "Timer Service", NotificationManager.IMPORTANCE_HIGH);
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}

		// Hide BottomNavigationView when keyboard is opened
		View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
		rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@SuppressLint("RestrictedApi")
			@Override
			public void onGlobalLayout() {
				int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
				if (heightDiff > dpToPx(getApplicationContext(), 200)) {
					// Keyboard is opened
					System.out.println("Keyboard is opened");
					BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
					bottomNavigationView.setVisibility(View.GONE);
				} else {
					// Keyboard is closed
					System.out.println("Keyboard is closed");
					BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
					bottomNavigationView.setVisibility(View.VISIBLE);
				}
			}
		});

		// Set content view and set first fragment
		setContentView(R.layout.activity_main);
		BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dayFragment).commit();
		bottomNavigationView.getMenu().findItem(R.id.today).setChecked(true);

		bottomNavigationView.setOnItemSelectedListener(x->{
			switch (x.getItemId()){
				case R.id.today:
					getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dayFragment).addToBackStack("dayFragment").commit();
					break;
				case R.id.notes:
					getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, notesFragment).addToBackStack("notesFragment").commit();
					break;
				case R.id.timer:
					getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, timerFragment).addToBackStack("timerFragment").commit();
					break;
			}
			return true;
		});
	}
}