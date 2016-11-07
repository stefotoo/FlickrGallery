package com.dision.android.flickrgallery;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;

import com.dision.android.flickrgallery.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTest {

    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void performFabClick() {
        onView(withId(R.id.fab)).perform(click());
    }

    @Test
    public void isToolbarNotVisible() {
        onView(withId(R.id.toolbar_activity_main)).check(doesNotExist());
    }

    @Test
    public void clickOnSecondRecyclerViewItem() {
        onView(withId(R.id.rv_gallery_items)).perform(RecyclerViewActions.scrollToPosition(10));
    }

    @Test
    public void pressBackToMainActivity() {
        //onView(withId(R.id.toolbar_activity_main)).perform(pressBack());
        pressBack();
    }


}
