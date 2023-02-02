package de.hdmstuttgart.bulletjournalapp.DayPackage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdmstuttgart.bulletjournalapp.BulletsPackage.Bullet;
import de.hdmstuttgart.bulletjournalapp.BulletsPackage.BulletCategories;
import de.hdmstuttgart.bulletjournalapp.BulletsPackage.BulletListAdapter;
import de.hdmstuttgart.bulletjournalapp.MainViewModel;
import de.hdmstuttgart.bulletjournalapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class DayFragment extends Fragment {

    MainViewModel viewModel;
    private Calendar calendar = Calendar.getInstance();
    private Calendar todayCalendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
    private MaterialToolbar topBarTitle;
    String date;

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
        // Code
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        // Notiz zum Datenzugriff: 1. Schicht: MainViewModel, 2. DayRepository, 3. DayDAO, 4. Tatsächliche Datenbank mit Queries
        // Nicht vergessen: notifyDataSetChanged() aufrufen, wenn die Liste geändert wurde

        // Linking the icons in top bar to load the next/previous day and its bullets
        MaterialToolbar toolbar = (MaterialToolbar) view.findViewById(R.id.topAppBar);
        toolbar.setOnMenuItemClickListener(item -> {
            // Next day >
            if (item.getItemId() == R.id.next_day) {
                calendar.add(Calendar.DATE, 1);
                updateDate();
                // If there's already a day existing with this date, then load its content, otherwise create a new day with this date
                if (viewModel.getDay(date) != null) {
                    Day selectedDay = viewModel.getDay(date);
                    // TODO: Bullets von diesem Tag laden und anzeigen
                }
                else {
                    Day nowInsertedDay = new Day(date, new ArrayList<Bullet>());
                    viewModel.insertNewDay(nowInsertedDay);
                }
                return true;
            }

            // Today •
            else if (item.getItemId() == R.id.today){
                calendar = Calendar.getInstance();
                updateDate();
                // If there's already a day existing with this date, then load its content, otherwise create a new day with this date2
                if (viewModel.getDay(date) != null) {
                    Day selectedDay = viewModel.getDay(date);
                    // TODO: Bullets von diesem Tag laden und anzeigen
                    // This isn't necessary, it's just for testing purposes. Usually the bullets would be loaded from the database.
                    Bullet sampleBullet = new Bullet("This is a sample bullet", BulletCategories.NOTE);
                    ArrayList<Bullet> bullets = new ArrayList<Bullet>(){{
                        add(sampleBullet);
                    }};
                    selectedDay.bullets = bullets;
                    // Load the bullets for the current day
                    RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewBullets);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    BulletListAdapter bulletListAdapter = new BulletListAdapter(selectedDay.bullets);
                    recyclerView.setAdapter(bulletListAdapter);
                    recyclerView.setHasFixedSize(true);
                    // Nicht vergessen: notifyDataSetChanged() aufrufen, wenn die Liste geändert wurde
                } else {
                    viewModel.insertNewDay(new Day(date, new ArrayList<Bullet>()));
                }
                return true;
            }

            // Previous day <
            else if (item.getItemId() == R.id.day_before){
                calendar.add(Calendar.DATE, -1);
                updateDate();
                // If there's already a day existing with this date, then load its content, otherwise create a new day with this date
                if (viewModel.getDay(date) != null) {
                    Day selectedDay = viewModel.getDay(date);
                    //TODO: Bullets von diesem Tag laden und anzeigen
                }
                else {
                    // This is a dummy bullet
                    Bullet sampleBullet = new Bullet("Sample Bullet", BulletCategories.NOTE);
                    ArrayList<Bullet> bullets = new ArrayList<Bullet>(){{
                        add(sampleBullet);
                    }};
                    Day nowInsertedDay = new Day(date, bullets);
                    viewModel.insertNewDay(nowInsertedDay);
                }
                return true;
            }
            return false;
        });

        MaterialToolbar topBarTitle = view.findViewById(R.id.topAppBar);

        // Show the date of the current day
        topBarTitle = view.findViewById(R.id.topAppBar);
        updateDate();

        // Defining the FABs
        ExtendedFloatingActionButton extended_fab_new_bullet = getView().findViewById(R.id.extended_fab_new_bullet);
        FloatingActionButton small_fab_note = getView().findViewById(R.id.fab_note);
        FloatingActionButton small_fab_event = getView().findViewById(R.id.small_fab_event);
        FloatingActionButton small_fab_task = getView().findViewById(R.id.small_fab_task);
        FloatingActionButton small_fab_daily_highlight = getView().findViewById(R.id.small_fab_daily_highlight);

        // Setting clicklisteners for the FABs
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
                // Get the current day, then add and show a new bullet of type note to the list and save it to the database
                Day currentDay = viewModel.getDay(date);
                System.out.println("HALLLLLOOOOO HIER 4 " + currentDay + " -----------------" + date);
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
            public void onClick(View v) {
                extended_fab_new_bullet.show();
                hideSmallFABs();
            }
        });
    }

    FloatingActionButton small_fab_note;
    FloatingActionButton small_fab_event;
    FloatingActionButton small_fab_task;
    FloatingActionButton small_fab_daily_highlight;

    // Method to hide the small FABs
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

    private void updateDate() {
        date = dateFormat.format(calendar.getTime());
        // Adding custom titles for today, yesterday and tomorrow
        if (calendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH)
                && calendar.get(Calendar.DATE) == todayCalendar.get(Calendar.DATE)) {
            topBarTitle.setTitle("Today, " + date);
        } else if (calendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH)
                && calendar.get(Calendar.DATE) == todayCalendar.get(Calendar.DATE) - 1) {
            topBarTitle.setTitle("Yesterday, " + date);
        } else if (calendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == todayCalendar.get(Calendar.MONTH)
                && calendar.get(Calendar.DATE) == todayCalendar.get(Calendar.DATE) + 1) {
            topBarTitle.setTitle("Tomorrow, " + date);
        } else {
            topBarTitle.setTitle(date);
        }
        }
    }