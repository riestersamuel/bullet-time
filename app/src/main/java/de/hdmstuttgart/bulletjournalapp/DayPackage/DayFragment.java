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
import android.widget.ImageButton;

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
    Day currentlySelectedDay;
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

        topBarTitle = view.findViewById(R.id.topAppBar);
        updateDate();


        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        // Notiz zum Datenzugriff: 1. Schicht: MainViewModel, 2. DayRepository, 3. DayDAO, 4. Tatsächliche Datenbank mit Queries
        // Nicht vergessen: notifyDataSetChanged() aufrufen, wenn die Liste geändert wurde

        // Linking the icons in top bar to load the next/previous day and its bullets
        MaterialToolbar toolbar = (MaterialToolbar) view.findViewById(R.id.topAppBar);

        // Load the bullets for the current day
        currentlySelectedDay = viewModel.getDay(date);
        if(currentlySelectedDay == null) {
            currentlySelectedDay = new Day(date, new ArrayList<Bullet>());
            viewModel.insertNewDay(currentlySelectedDay);
        }
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewBullets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        BulletListAdapter bulletListAdapter = new BulletListAdapter(currentlySelectedDay.bullets, (bullet,position) -> {
            // TODO: Methode um geänderten Content zu speichern
        }, (bullet,position) ->{
            // TODO: Methode um eine Bullet beim Anklicken abzuhaken
            changeBulletStatus(bullet, position);
        });
        recyclerView.setAdapter(bulletListAdapter);
        recyclerView.setHasFixedSize(true);
        // Nicht vergessen: notifyDataSetChanged() aufrufen, wenn die Liste geändert wurde

        toolbar.setOnMenuItemClickListener(item -> {
            // Next day >
            if (item.getItemId() == R.id.next_day) {
                calendar.add(Calendar.DATE, 1);
                updateDate();
            }

            // Today •
            else if (item.getItemId() == R.id.today){
                calendar = Calendar.getInstance();
                updateDate();
            }

            // Previous day <
            else if (item.getItemId() == R.id.day_before){
                calendar.add(Calendar.DATE, -1);
                updateDate();
            }

            // Setting the adapter to the new bullets
            // If there's already a day existing with this date, then load its content, otherwise create a new day with this date
            if (viewModel.getDay(date) != null) {
                currentlySelectedDay = viewModel.getDay(date);
                System.out.println("Day already exists");
            }
            else {
                currentlySelectedDay = new Day(date, new ArrayList<Bullet>());
                viewModel.insertNewDay(currentlySelectedDay);
                System.out.println("Day inserted");
            }

            recyclerView.setAdapter(new BulletListAdapter(currentlySelectedDay.bullets, (bullet,position) -> {
                // TODO: Methode um eine Bullet nach dem ändern zu speichern
            }, (bullet,position) ->{
                // TODO: Methode um eine Bullet beim Anklicken abzuhaken
                changeBulletStatus(bullet, position);
            }));
            bulletListAdapter.notifyItemInserted(currentlySelectedDay.bullets.size() - 1);
            bulletListAdapter.notifyDataSetChanged();
            return false;
        });

        MaterialToolbar topBarTitle = view.findViewById(R.id.topAppBar);
        // Get today's date NEW
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
                // TODO: Add a new note
            }
        });
        small_fab_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add a new event
            }
        });
        small_fab_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Add a new task
            }
        });
        small_fab_daily_highlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Add a new daily highlight
                // Get the current day, then add and show a new bullet of type note to the list and save it to the database
                currentlySelectedDay.bullets.add(new Bullet("Diese App fertig programmieren", BulletCategories.DAILY_HIGHLIGHT));
                viewModel.updateDay(currentlySelectedDay);
                bulletListAdapter.notifyItemInserted(currentlySelectedDay.bullets.size() - 1);
                //bulletListAdapter.notifyItemRangeChanged(0, currentlySelectedDay.bullets.size());
                bulletListAdapter.notifyDataSetChanged();
                hideSmallFABs();
                extended_fab_new_bullet.show();
            }
        });
    }

    private void changeBulletStatus(Bullet bullet, int position) {
        ImageButton imageButton = getView().findViewById(R.id.imageButton);
        // Flip through the maximal of two status icons
        bullet.setChecked(!bullet.isChecked());
        if (bullet.isChecked() && bullet.getCategory() == BulletCategories.DAILY_HIGHLIGHT) {
            imageButton.setImageResource(R.drawable.baseline_event_checked_24);
        }
        if (!bullet.isChecked() && bullet.getCategory() == BulletCategories.DAILY_HIGHLIGHT) {
            imageButton.setImageResource(R.drawable.baseline_event_unchecked_24);
        }
        if (bullet.isChecked() && bullet.getCategory() == BulletCategories.TASK) {
            imageButton.setImageResource(R.drawable.baseline_check_box_24);
        }
        if (!bullet.isChecked() && bullet.getCategory() == BulletCategories.TASK) {
            imageButton.setImageResource(R.drawable.baseline_check_box_outline_blank_24);
        }
        if (bullet.isChecked() && bullet.getCategory() == BulletCategories.DAILY_HIGHLIGHT) {
            imageButton.setImageResource(R.drawable.baseline_star_24);
        }
        if (!bullet.isChecked() && bullet.getCategory() == BulletCategories.DAILY_HIGHLIGHT) {
            imageButton.setImageResource(R.drawable.baseline_star_outline_24);
        }
        else System.out.println("Error: No category found");
        // Save the changes to the database
        viewModel.updateDay(currentlySelectedDay);
        System.out.println("Bullet status changed");
        System.out.println(currentlySelectedDay);
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