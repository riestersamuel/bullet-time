package de.hdmstuttgart.bulletjournalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

	DayFragment dayFragment = new DayFragment();
	NotesFragment notesFragment = new NotesFragment();
	ProfileFragment profileFragment = new ProfileFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dayFragment).commit();

		bottomNavigationView.setOnItemSelectedListener(x->{
			switch (x.getItemId()){
				case R.id.today:
					getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dayFragment).commit();
					break;
				case R.id.notes:
					getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotesFragment()).commit();
					break;
				case R.id.profile:
					getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
					break;
			}
			return true;
		});
	}
}