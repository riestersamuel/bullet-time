package de.hdmstuttgart.bulletjournalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdmstuttgart.bulletjournalapp.DayPackage.DayFragment;
import de.hdmstuttgart.bulletjournalapp.NotePackage.NotesFragment;
import de.hdmstuttgart.bulletjournalapp.TimerPackage.TimerFragment;

public class MainActivity extends AppCompatActivity {

	DayFragment dayFragment = new DayFragment();
	NotesFragment notesFragment = new NotesFragment();
	TimerFragment profileFragment = new TimerFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dayFragment).commit();
		bottomNavigationView.getMenu().findItem(R.id.today).setChecked(true);

		bottomNavigationView.setOnItemSelectedListener(x->{
			switch (x.getItemId()){
				case R.id.today:
					getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dayFragment).commit();
					break;
				case R.id.notes:
					getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotesFragment()).commit();
					break;
				case R.id.timer:
					getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TimerFragment()).commit();
					break;
			}
			return true;
		});
	}
}