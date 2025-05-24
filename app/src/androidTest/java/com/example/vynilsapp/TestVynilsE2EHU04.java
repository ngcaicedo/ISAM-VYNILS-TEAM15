package com.example.vynilsapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestVynilsE2EHU04 {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    public void configureGlobalTimeout() {
        // Configurar tiempo de espera para operaciones asíncronas
        IdlingPolicies.setIdlingResourceTimeout(8, TimeUnit.SECONDS);

        // Configurar tiempo de espera para interacciones con la UI
        IdlingPolicies.setMasterPolicyTimeout(8, TimeUnit.SECONDS);
    }

    @Test
    public void mainActivityTestPerformerDetailBandGuest() {
        configureGlobalTimeout();

        ViewInteraction guestBtn = onView(allOf(withId(R.id.btnSeeGuestMenu), isDisplayed()));
        guestBtn.perform(click());

        ViewInteraction performerCatalogBtn = onView(allOf(withId(R.id.btn_see_performer_catalog), isDisplayed()));
        performerCatalogBtn.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(withText("Queen"), isDisplayed()))
                .perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Obtener textos traducidos desde recursos
        final List<String> expectedLabels = new ArrayList<>();
        mActivityTestRule.getScenario().onActivity(activity -> {
            expectedLabels.add(activity.getString(R.string.performer_name));
            expectedLabels.add(activity.getString(R.string.performer_creation_date));
            expectedLabels.add(activity.getString(R.string.performer_description));
        });

        // Validar que los labels traducidos están visibles
        for (String label : expectedLabels) {
            onView(allOf(withText(label), isDisplayed()))
                    .check(matches(withText(label)));
        }

        onView(withText("Queen")).check(matches(isDisplayed()));
        onView(withSubstring("Queen es una banda británica de rock formada en 1970 en Londres")).check(matches(isDisplayed()));
        onView(withText("1970-01-01")).check(matches(isDisplayed()));
    }

    @Test
    public void mainActivityTestPerformerDetailMusicianGuest() {
        configureGlobalTimeout();

        ViewInteraction guestBtn = onView(allOf(withId(R.id.btnSeeGuestMenu), isDisplayed()));
        guestBtn.perform(click());

        ViewInteraction performerCatalogBtn = onView(allOf(withId(R.id.btn_see_performer_catalog), isDisplayed()));
        performerCatalogBtn.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(withText("Rubén Blades Bellido de Luna"), isDisplayed()))
                .perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Obtener textos traducidos desde recursos
        final List<String> expectedLabels = new ArrayList<>();
        mActivityTestRule.getScenario().onActivity(activity -> {
            expectedLabels.add(activity.getString(R.string.performer_name));
            expectedLabels.add(activity.getString(R.string.performer_birth_date));
            expectedLabels.add(activity.getString(R.string.performer_description));
        });

        // Validar que los labels traducidos están visibles
        for (String label : expectedLabels) {
            onView(allOf(withText(label), isDisplayed()))
                    .check(matches(withText(label)));
        }

        onView(withText("Rubén Blades Bellido de Luna")).check(matches(isDisplayed()));
        onView(withSubstring("Es un cantante, compositor, músico, actor, abogado, político y activista")).check(matches(isDisplayed()));
        onView(withText("1948-07-16")).check(matches(isDisplayed()));
    }

    @Test
    public void mainActivityTestPerformerDetailBandCollector() {
        configureGlobalTimeout();

        ViewInteraction guestBtn = onView(allOf(withId(R.id.btnSeeCollectorMenu), isDisplayed()));
        guestBtn.perform(click());

        ViewInteraction performerCatalogBtn = onView(allOf(withId(R.id.btn_see_performer_catalog), isDisplayed()));
        performerCatalogBtn.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(withText("Queen"), isDisplayed()))
                .perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Obtener textos traducidos desde recursos
        final List<String> expectedLabels = new ArrayList<>();
        mActivityTestRule.getScenario().onActivity(activity -> {
            expectedLabels.add(activity.getString(R.string.performer_name));
            expectedLabels.add(activity.getString(R.string.performer_creation_date));
            expectedLabels.add(activity.getString(R.string.performer_description));
        });

        // Validar que los labels traducidos están visibles
        for (String label : expectedLabels) {
            onView(allOf(withText(label), isDisplayed()))
                    .check(matches(withText(label)));
        }

        onView(withText("Queen")).check(matches(isDisplayed()));
        onView(withSubstring("Queen es una banda británica de rock formada en 1970 en Londres")).check(matches(isDisplayed()));
        onView(withText("1970-01-01")).check(matches(isDisplayed()));
    }

    @Test
    public void mainActivityTestPerformerDetailMusicianCollector() {
        configureGlobalTimeout();

        ViewInteraction guestBtn = onView(allOf(withId(R.id.btnSeeCollectorMenu), isDisplayed()));
        guestBtn.perform(click());

        ViewInteraction performerCatalogBtn = onView(allOf(withId(R.id.btn_see_performer_catalog), isDisplayed()));
        performerCatalogBtn.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(withText("Rubén Blades Bellido de Luna"), isDisplayed()))
                .perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Obtener textos traducidos desde recursos
        final List<String> expectedLabels = new ArrayList<>();
        mActivityTestRule.getScenario().onActivity(activity -> {
            expectedLabels.add(activity.getString(R.string.performer_name));
            expectedLabels.add(activity.getString(R.string.performer_birth_date));
            expectedLabels.add(activity.getString(R.string.performer_description));
        });

        // Validar que los labels traducidos están visibles
        for (String label : expectedLabels) {
            onView(allOf(withText(label), isDisplayed()))
                    .check(matches(withText(label)));
        }

        onView(withText("Rubén Blades Bellido de Luna")).check(matches(isDisplayed()));
        onView(withSubstring("Es un cantante, compositor, músico, actor, abogado, político y activista")).check(matches(isDisplayed()));
        onView(withText("1948-07-16")).check(matches(isDisplayed()));
    }
}


