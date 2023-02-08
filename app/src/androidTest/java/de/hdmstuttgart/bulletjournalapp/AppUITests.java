package de.hdmstuttgart.bulletjournalapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AppUITests {
	@Rule
	public ActivityScenarioRule<MainActivity> activityScenarioRule
			= new ActivityScenarioRule<>(MainActivity.class);


	@Test
	public void addBulletTest(){
		onView(withId(R.id.extended_fab_new_bullet)).perform(click());
		onView(withId(R.id.small_fab_daily_highlight)).perform(click());
		onView(withId(R.id.recyclerViewBullets)).check(matches(hasDescendant(withText(""))));
	}

	@Test
	public void changeDay(){
		onView(withId(R.id.next_day)).perform(click());
		onView(withId(R.id.next_day)).perform(click());
		onView(withId(R.id.today_Bullet)).perform(click());
		onView(withId(R.id.day_before)).perform(click());
	}

	@Test
	public void startTimerTest(){
		onView(withId(R.id.timer)).perform(click());
		onView(withId(R.id.extended_fab_start)).perform(click());
		onView(withId(R.id.remaining_time)).check(matches(withText("25")));
		onView(withId(R.id.extended_fab_stop)).perform(click());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		onView(withId(R.id.extended_fab_start)).perform(click());
		onView(withId(R.id.notes)).perform(click());
		onView(withId(R.id.timer)).perform(click());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		onView(withId(R.id.remaining_time)).check(matches(withText("25")));
	}

	@Test
	public void addNoteTest(){
		onView(withId(R.id.notes)).perform(click());
		onView(withId(R.id.extended_fab_note)).perform(click());
		onView(withId(R.id.ET_Title)).perform(replaceText("Test"), closeSoftKeyboard());
		onView(withId(R.id.ET_Content)).perform(replaceText("Test"), closeSoftKeyboard());
		onView(withId(R.id.topAppBar)).perform(click());
	}
}