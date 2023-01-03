package de.hdmstuttgart.bulletjournalapp;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class TimerFragment extends Fragment {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	public TimerFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment ProfileFragment.
	 */

	// TODO: Rename and change types and number of parameters
	public static TimerFragment newInstance(String param1, String param2) {
		TimerFragment fragment = new TimerFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_timer, container, false);

		return view;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Defining the UI elements
		ExtendedFloatingActionButton extended_fab_start = getView().findViewById(R.id.extended_fab_start);
		ExtendedFloatingActionButton extended_fab_stop = getView().findViewById(R.id.extended_fab_stop);
		TextView information_text = getView().findViewById(R.id.information_text);
		TextView remaining_time = getView().findViewById(R.id.remaining_time);
		remaining_time.setText("");
		information_text.setText("Focus session: Start a pomodoro timer and only focus on your most important task for the next 25 minutes. No distractions allowed! After that, take a 5 minute break and repeat the process. After four focussed sessions, take a longer break of 15 minutes and afterwards restart the process.");

		CountDownTimer shortBreakTimer = new CountDownTimer(5000, 6000) {
			@Override
			public void onTick(long millisUntilFinished) {
				int remainingTime = (int) (millisUntilFinished / 60000 + 1);
				remaining_time.setText("" + remainingTime);
				information_text.setText("minutes of your short break remain. Enjoy the break!");
			}

			@Override
			public void onFinish() {
				// Rufe die Funktion auf, die den Timer startet (also die Funktion, die der extended_fab_start onClickListener aufruft)
				extended_fab_start.callOnClick();
			}
		};

		CountDownTimer longBreakTimer = new CountDownTimer(5000, 6000) {
			@Override
			public void onTick(long millisUntilFinished) {
				int remainingTime = (int) (millisUntilFinished / 60000 + 1);
				remaining_time.setText("" + remainingTime);
				information_text.setText("minutes of your long break remain. Enjoy the break!");
			}

			@Override
			public void onFinish() {
				// Rufe die Funktion auf, die den Timer startet (also die Funktion, die der extended_fab_start onClickListener aufruft)
				extended_fab_start.callOnClick();
			}
		};

		// Our pomodoro timer, here set to 25 minutes (1500000 milliseconds)
		// Every minute, change the UI (60000 milliseconds)
		// TODO: Diese Klasse auslagern und die millisInFuture wieder zurück auf 1500000 und den countdownInterval auf 60000 setzen
		CountDownTimer timer = new CountDownTimer(5000, 6000) {
			// The remaining minutes
			int breakCounter = 0;
			@Override
			public void onTick(long millisUntilFinished) {
				int remainingTime = (int) (millisUntilFinished / 60000 + 1);
				remaining_time.setText("" + remainingTime);
			}

			@Override
			public void onFinish() {
				// End the animation
				ImageView clockAnimation = view.findViewById(R.id.clock_animation);
				clockAnimation.setVisibility(View.INVISIBLE);
				((AnimationDrawable) ((ImageView) view.findViewById(R.id.clock_animation)).getDrawable()).stop();
				breakCounter++;
				if (breakCounter % 4 == 0) {
					longBreakTimer.start();
				}
				else {
					shortBreakTimer.start();
				}
				}
		};

		// Adding ClickListeners to the buttons
		extended_fab_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				remaining_time.setText("25");
				information_text.setText("minutes of your pomodoro session remain. Focus on your most important task!");
				extended_fab_start.setVisibility(View.INVISIBLE);
				extended_fab_stop.setVisibility(View.VISIBLE);
				timer.start();

				// That's for the timer animation
				// Get a reference to the ImageView
				ImageView clockAnimation = view.findViewById(R.id.clock_animation);
				clockAnimation.setVisibility(View.VISIBLE);
				// Set the animation-list drawable as the src attribute
				clockAnimation.setImageResource(R.drawable.clock_animation);
				// Start the animation
				((AnimationDrawable) clockAnimation.getDrawable()).start();
			}
		});

		extended_fab_stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				information_text.setText("You stopped the timer. Start a new one.");
				extended_fab_start.setVisibility(View.VISIBLE);
				extended_fab_stop.setVisibility(View.INVISIBLE);
				timer.cancel();
				// Reset the remaining time
				remaining_time.setText("");

				// That's for the timer animation
				ImageView clockAnimation = view.findViewById(R.id.clock_animation);
				clockAnimation.setVisibility(View.INVISIBLE);
				((AnimationDrawable) ((ImageView) view.findViewById(R.id.clock_animation)).getDrawable()).stop();
			}
		});
	}
}