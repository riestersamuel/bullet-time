package de.hdmstuttgart.bulletjournalapp;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		// Must be last line of this method
		return inflater.inflate(R.layout.fragment_timer, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Defining the UI elements
		ExtendedFloatingActionButton extended_fab_start = getView().findViewById(R.id.extended_fab_start);
		ExtendedFloatingActionButton extended_fab_stop = getView().findViewById(R.id.extended_fab_stop);
		FloatingActionButton small_fab_pause = getView().findViewById(R.id.small_fab_pause);
		TextView information_text = getView().findViewById(R.id.information_text);
		TextView remaining_time = getView().findViewById(R.id.remaining_time);

		// Adding ClickListeners to the buttons
		extended_fab_start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				extended_fab_start.setVisibility(View.INVISIBLE);
				extended_fab_stop.setVisibility(View.VISIBLE);
				small_fab_pause.setVisibility(View.VISIBLE);
			}
		});

		extended_fab_stop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				extended_fab_start.setVisibility(View.VISIBLE);
				extended_fab_stop.setVisibility(View.INVISIBLE);
				small_fab_pause.setVisibility(View.INVISIBLE);
			}
		});
	}
}