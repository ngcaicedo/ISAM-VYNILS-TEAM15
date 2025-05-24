package com.example.vynilsapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.vynilsapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestVynilsE2EHU08 {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    public void configureGlobalTimeout() {
        // Configurar tiempo de espera para operaciones asíncronas
        IdlingPolicies.setIdlingResourceTimeout(5, TimeUnit.SECONDS);

        // Configurar tiempo de espera para interacciones con la UI
        IdlingPolicies.setMasterPolicyTimeout(5, TimeUnit.SECONDS);
    }

    @Test
    public void mainActivityTestCollector() {
        configureGlobalTimeout();

        ViewInteraction guestBtn = onView(allOf(withId(R.id.btnSeeCollectorMenu), isDisplayed()));
        guestBtn.perform(click());

        ViewInteraction albumsCatalogBtn = onView(allOf(withId(R.id.btn_see_album_catalog), isDisplayed()));
        albumsCatalogBtn.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.btnAddTrack)).check(matches(isDisplayed()));

        ViewInteraction createAlbumBtn = onView(allOf(withId(R.id.btnAddTrack), isDisplayed()));
        createAlbumBtn.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.etName)).check(matches(isDisplayed()));
        onView(withId(R.id.etDuration)).check(matches(isDisplayed()));
        onView(withId(R.id.actvSelectAlbum)).check(matches(isDisplayed()));
        onView(withId(R.id.btnSaveTrackAlbum)).check(matches(isDisplayed()));

        ViewInteraction albumNameTxt = onView(withId(R.id.etName));
        albumNameTxt.perform(scrollTo(), replaceText("Track_test"), closeSoftKeyboard());

        ViewInteraction albumCoverTxt = onView(withId(R.id.etDuration));
        albumCoverTxt.perform(scrollTo(), replaceText("2"), closeSoftKeyboard());

        ViewInteraction albumGenreTxt = onView(withId(R.id.actvSelectAlbum));
        albumGenreTxt.perform(scrollTo(), replaceText("Buscando América"), closeSoftKeyboard());

        ViewInteraction saveAlbumBtn = onView(allOf(withId(R.id.btnSaveTrackAlbum), isDisplayed()));
        saveAlbumBtn.perform(click());
    }
}