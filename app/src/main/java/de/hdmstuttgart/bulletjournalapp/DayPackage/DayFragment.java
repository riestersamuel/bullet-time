package de.hdmstuttgart.bulletjournalapp.DayPackage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdmstuttgart.bulletjournalapp.BulletsPackage.Bullet;
import de.hdmstuttgart.bulletjournalapp.BulletsPackage.BulletCategories;
import de.hdmstuttgart.bulletjournalapp.BulletsPackage.BulletListAdapter;
import de.hdmstuttgart.bulletjournalapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DayFragment extends Fragment {

    Bullet sampleBullet = new Bullet("Sample Bullet", BulletCategories.NOTE);
    ArrayList<Bullet> bullets = new ArrayList<Bullet>(){{
        add(sampleBullet);
    }};
    Day sampleDay = new Day(LocalDateTime.now().toString(), bullets);

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DayFragment newInstance(String param1, String param2) {
        DayFragment fragment = new DayFragment();
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
        View view = inflater.inflate(R.layout.fragment_day, container, false);

        // TODO: Load today's day from database and display the bullets
        // if day != null dann neues day objekt erstellen



        // Get today's date
        // Create a Date object
        Date currentDate = Calendar.getInstance().getTime();
        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat sdf = new SimpleDateFormat("dd. MMMM");
        // Use the SimpleDateFormat object to format the Date object
        String formattedDate = sdf.format(currentDate);
        // Get a reference to the MaterialToolbar view
        MaterialToolbar topBarTitle = view.findViewById(R.id.topAppBar);

        // Set the title for the MaterialToolbar
        topBarTitle.setTitle("Today, " + formattedDate);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // RecyclerView
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewBullets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        BulletListAdapter bulletListAdapter = new BulletListAdapter(sampleDay.bullets);
        recyclerView.setAdapter(bulletListAdapter);
        recyclerView.setHasFixedSize(true);

        // Defining the UI elements
        ExtendedFloatingActionButton extended_fab_new_bullet = getView().findViewById(R.id.extended_fab_new_bullet);
        FloatingActionButton small_fab_note = getView().findViewById(R.id.fab_note);
        FloatingActionButton small_fab_event = getView().findViewById(R.id.small_fab_event);
        FloatingActionButton small_fab_task = getView().findViewById(R.id.small_fab_task);
        FloatingActionButton small_fab_daily_highlight = getView().findViewById(R.id.small_fab_daily_highlight);

        // Setting clicklisteners

        // Arrows in top bar
        //TODO: Datenbankanknüpfung mit den Pfeilen
            // 1. Schicht: MainViewModel, 2. Neues Repository, 3. DayDAO, 4. Tatsächliche Datenbank


        // Floating action buttons
        extended_fab_new_bullet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extended_fab_new_bullet.hide();
                small_fab_note.show();
                small_fab_event.show();
                small_fab_task.show();
                small_fab_daily_highlight.show();
            }
        });
        small_fab_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extended_fab_new_bullet.show();
                hideSmallFABs();
            }
        });
        small_fab_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extended_fab_new_bullet.show();
                hideSmallFABs();
            }
        });
        small_fab_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extended_fab_new_bullet.show();
                hideSmallFABs();
            }
        });
        small_fab_daily_highlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });
    }

    // Method to hide the small FABs
    FloatingActionButton small_fab_note;
    FloatingActionButton small_fab_event;
    FloatingActionButton small_fab_task;
    FloatingActionButton small_fab_daily_highlight;

    public void hideSmallFABs() {
        if (small_fab_note == null) {
            small_fab_note = getView().findViewById(R.id.fab_note);
        }
        if (small_fab_event == null) {
            small_fab_event = getView().findViewById(R.id.small_fab_event);
        }
        if (small_fab_task == null) {
            small_fab_task = getView().findViewById(R.id.small_fab_task);
        }
        if (small_fab_daily_highlight == null) {
            small_fab_daily_highlight = getView().findViewById(R.id.small_fab_daily_highlight);
        }
        small_fab_note.hide();
        small_fab_event.hide();
        small_fab_task.hide();
        small_fab_daily_highlight.hide();
    }
}