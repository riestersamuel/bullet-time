package de.hdmstuttgart.bulletjournalapp.TimerPackage;

import android.animation.ObjectAnimator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import de.hdmstuttgart.bulletjournalapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class TimerFragment extends Fragment {

    private CountDownTimer timer;
	private CountDownTimer shortBreakTimer;
	private CountDownTimer longBreakTimer;
	private Intent timerServiceIntent;
	private Intent shortBreakServiceIntent;
	private Intent longBreakServiceIntent;
	private boolean isTimerRunning;
	private long remainingTime;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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

        // Get a reference to the MaterialToolbar view
        MaterialToolbar topBar = view.findViewById(R.id.topAppBar);

        // Set the title for the MaterialToolbar
        topBar.setTitle("Pomodoro Timer");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Defining the UI elements
        ExtendedFloatingActionButton extended_fab_start = getView().findViewById(R.id.extended_fab_start);
        ExtendedFloatingActionButton extended_fab_stop = getView().findViewById(R.id.extended_fab_stop);
        TextView information_text = getView().findViewById(R.id.information_text);
		ConstraintLayout container = getView().findViewById(R.id.container);
		TextView remaining_time = getView().findViewById(R.id.remaining_time);
		ProgressBar progressBar = getView().findViewById(R.id.progressBarTimer);
		information_text.setText("Currently no timer is running. Start a new timer or find out how this works by clicking on the question mark in the top right.");

		// If the timer is running, show the stop button and hide the start button
		if(TimerHolder.getInstance().getTimer() != null) {
			information_text.setText("minute(s) of your pomodoro session remain. \nFocus on your most important task!");
			extended_fab_start.hide();
			extended_fab_stop.show();

			timer = TimerHolder.getInstance().getTimer();
			// That's for the timer animation
			// Get a reference to the ImageView
			ImageView clockAnimation = view.findViewById(R.id.clock_animation);
			clockAnimation.setVisibility(View.VISIBLE);
			// Set the animation-list drawable as the src attribute
			clockAnimation.setImageResource(R.drawable.clock_animation);
			// Start the animation
			((AnimationDrawable) clockAnimation.getDrawable()).start();
			remaining_time.setText(String.valueOf(remainingTime));
			// Hide tomato image
			ImageView tomato = view.findViewById(R.id.tomatoImage);
			tomato.setVisibility(View.GONE);
			progressBar.setVisibility(View.VISIBLE);
			container.setVisibility(View.VISIBLE);
			startAnimation(view);
		}

        shortBreakTimer = new CountDownTimer(300000, 1000) {
			final double onePercent = (double)100/300000;
            @Override
            public void onTick(long millisUntilFinished) {
				remainingTime = (int) (millisUntilFinished / 60000 + 1);
				int progress = (int)(onePercent * millisUntilFinished);
				ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress",progress);
				animation.setDuration(1000); // 0.5 second
				animation.setInterpolator(new DecelerateInterpolator());
				animation.start();
                remaining_time.setText("" + remainingTime);
                information_text.setText("minute(s) of your short break remain. \nEnjoy the break!");
            }

            @Override
            public void onFinish() {
				requireContext().stopService(shortBreakServiceIntent);
				timerServiceIntent = new Intent(getActivity(), TimerService.class);
				TimerHolder.getInstance().setTimer(timer);
				requireContext().startService(timerServiceIntent);
				showShortBreakNotification();
            }
        };
        longBreakTimer = new CountDownTimer(1200000, 1000) {
			final double onePercent = (double)100/1200000;
            @Override
            public void onTick(long millisUntilFinished) {
				remainingTime = (int) (millisUntilFinished / 60000 + 1);
				int progress = (int)(onePercent * millisUntilFinished);
				ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress",progress);
				animation.setDuration(500); // 0.5 second
				animation.setInterpolator(new DecelerateInterpolator());
				animation.start();
                remaining_time.setText("" + remainingTime);
                information_text.setText("minute(s) of your long break remain. Enjoy the break!");
            }

            @Override
            public void onFinish() {
                // Rufe die Funktion auf, die den Timer startet (also die Funktion, die der extended_fab_start onClickListener aufruft)
				requireContext().stopService(longBreakServiceIntent);
				timerServiceIntent = new Intent(getActivity(), TimerService.class);
				TimerHolder.getInstance().setTimer(timer);
				requireContext().startService(timerServiceIntent);
				showLongBreakNotification();
            }
        };

        // Our pomodoro timer, here set to 25 minutes (1500000 milliseconds)
        // Every minute, change the UI (60000 milliseconds)
        // TODO: Diese Klasse auslagern
        timer = new CountDownTimer(1500000, 1000) {
			// The remaining minutes
			int breakCounter = 0;
			final double onePercent = (double)100/1500000;

            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = (int) (millisUntilFinished / 60000 + 1);
				remaining_time.setText("" + remainingTime);
				int progress = (int)(onePercent * millisUntilFinished);
				ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress",progress);
				animation.setDuration(1000); // 0.5 second
				animation.setInterpolator(new DecelerateInterpolator());
				animation.start();

            }

            @Override
            public void onFinish() {
                // End the animation
                ImageView clockAnimation = view.findViewById(R.id.clock_animation);
                clockAnimation.setVisibility(View.INVISIBLE);
                ((AnimationDrawable) ((ImageView) view.findViewById(R.id.clock_animation)).getDrawable()).stop();
                breakCounter++;
                if (breakCounter % 4 == 0) {
					if(longBreakServiceIntent == null) {
						longBreakServiceIntent = new Intent(getActivity(), TimerService.class);
						TimerHolder.getInstance().setLongBreakTimer(longBreakTimer);
						requireContext().startService(longBreakServiceIntent);
					}
                } else {
					if(shortBreakServiceIntent == null) {
						shortBreakServiceIntent = new Intent(getActivity(), TimerService.class);
						TimerHolder.getInstance().setShortBreakTimer(shortBreakTimer);
						shortBreakTimer.start();
						requireContext().startService(shortBreakServiceIntent);
					}
                }
				startAnimation(view);
				showTimerFinishedNotification();
			}
        };

        // Adding ClickListeners to the buttons
        extended_fab_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remaining_time.setText("25");
                information_text.setText("minute(s) of your pomodoro session remain. \nFocus on your most important task!");
                extended_fab_start.hide();
                extended_fab_stop.show();
				container.setVisibility(View.VISIBLE);
				if(timerServiceIntent == null){
					timerServiceIntent = new Intent(getActivity(), TimerService.class);
					TimerHolder.getInstance().setTimer(timer);
					requireContext().startService(timerServiceIntent);
				}

                // That's for the timer animation
                // Get a reference to the ImageView
                ImageView clockAnimation = view.findViewById(R.id.clock_animation);
                clockAnimation.setVisibility(View.VISIBLE);
                // Set the animation-list drawable as the src attribute
                clockAnimation.setImageResource(R.drawable.clock_animation);
                // Start the animation
                ((AnimationDrawable) clockAnimation.getDrawable()).start();

                // Hide tomato image
                ImageView tomato = view.findViewById(R.id.tomatoImage);
                tomato.setVisibility(View.GONE);

				ProgressBar progressBar = view.findViewById(R.id.progressBarTimer);
				progressBar.setVisibility(View.VISIBLE);
            }
        });

        extended_fab_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information_text.setText("You stopped the timer. Start a new one.");
                extended_fab_start.show();
                extended_fab_stop.hide();
                timer.cancel();
				requireContext().stopService(timerServiceIntent);
				TimerHolder.getInstance().setTimer(null);
				TimerHolder.getInstance().setShortBreakTimer(null);
				TimerHolder.getInstance().setLongBreakTimer(null);
				timerServiceIntent = null;
				progressBar.setVisibility(View.INVISIBLE);
				progressBar.setProgress(0);

                shortBreakTimer.cancel();
                longBreakTimer.cancel();
                // Reset the remaining time
                remaining_time.setText("");
                // End the animation
                stopAnimation();
                // Hide tomato image
                ImageView tomato = view.findViewById(R.id.tomatoImage);
                tomato.setVisibility(View.GONE);
            }
        });

        // Linking the questionmark icon to the video
        MaterialToolbar toolbar = (MaterialToolbar) view.findViewById(R.id.topAppBar);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.help) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=1pADI_eZ_-U"));
                startActivity(browserIntent);
                return true;
            }
            return false;
        });
    }

    private void startAnimation(View view) {
        // That's for the timer animation
		ImageView tomato = view.findViewById(R.id.tomatoImage);
		tomato.setVisibility(View.GONE);
        ImageView clockAnimation = view.findViewById(R.id.clock_animation);
        clockAnimation.setVisibility(View.VISIBLE);
		clockAnimation.setImageResource(R.drawable.clock_animation);
		((AnimationDrawable)clockAnimation.getDrawable()).start();
    }

    private void stopAnimation() {
        // That's for the timer animation
        View view = super.getView();
		ConstraintLayout container = getView().findViewById(R.id.container);
        ImageView clockAnimation = view.findViewById(R.id.clock_animation);
        clockAnimation.setVisibility(View.GONE);
		container.setVisibility(View.GONE);
        ((AnimationDrawable) ((ImageView) view.findViewById(R.id.clock_animation)).getDrawable()).stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stop the timer when the fragment is paused
        if (timer != null) timer.cancel();
        if (shortBreakTimer != null) shortBreakTimer.cancel();
        if (longBreakTimer != null) longBreakTimer.cancel();
    }

	// Notifications for the normal timer
	private void showTimerFinishedNotification() {
		// Create a notification channel
		String channelId = "timer_finished";
		String channelName = "Timer Finished";
		int importance = NotificationManager.IMPORTANCE_HIGH;
		NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);

		// Get the notification manager
		NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.createNotificationChannel(channel);

		// Create a notification builder
		NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), channelId)
				.setSmallIcon(R.drawable.ic_baseline_timer_24)
				.setContentTitle("Pomodoro Timer")
				.setContentText("Time's up! Starting a short break now.")
				.setAutoCancel(true);

		// Show the notification
		notificationManager.notify(1, builder.build());
	}

	// Notifications for the short break timer
	private void showShortBreakNotification() {
		// Create a notification channel
		String channelId = "short_break";
		String channelName = "Short Break";
		int importance = NotificationManager.IMPORTANCE_HIGH;
		NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);

		// Get the notification manager
		NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.createNotificationChannel(channel);

		// Create a notification builder
		NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), channelId)
				.setSmallIcon(R.drawable.ic_baseline_timer_24)
				.setContentTitle("Pomodoro Timer")
				.setContentText("Break over! Starting a new session now.")
				.setAutoCancel(true);

		// Show the notification
		notificationManager.notify(2, builder.build());
	}

	// Notifications for the long break timer
	private void showLongBreakNotification() {
		// Create a notification channel
		String channelId = "long_break";
		String channelName = "Long Break";
		int importance = NotificationManager.IMPORTANCE_HIGH;
		NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);

		// Get the notification manager
		NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.createNotificationChannel(channel);

		// Create a notification builder
		NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), channelId)
				.setSmallIcon(R.drawable.ic_baseline_timer_24)
				.setContentTitle("Pomodoro Timer")
				.setContentText("Break over! Starting a new pomodoro session now.")
				.setAutoCancel(true);

		// Show the notification
		notificationManager.notify(3, builder.build());
	}


}