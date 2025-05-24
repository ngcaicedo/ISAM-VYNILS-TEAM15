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
public class TestVynilsE2EHU06 {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    public void configureGlobalTimeout() {
        // Configurar tiempo de espera para operaciones asíncronas
        IdlingPolicies.setIdlingResourceTimeout(8, TimeUnit.SECONDS);

        // Configurar tiempo de espera para interacciones con la UI
        IdlingPolicies.setMasterPolicyTimeout(8, TimeUnit.SECONDS);
    }

    @Test
    public void mainActivityTestCollectorDetailGuest() {
        configureGlobalTimeout();

        ViewInteraction guestBtn = onView(allOf(withId(R.id.btnSeeGuestMenu), isDisplayed()));
        guestBtn.perform(click());

        ViewInteraction collectorCatalogBtn = onView(allOf(withId(R.id.btn_see_collectos), isDisplayed()));
        collectorCatalogBtn.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(withText("Manolo Bellon"), isDisplayed()))
                .perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Obtener textos traducidos desde recursos
        final List<String> expectedLabels = new ArrayList<>();
        mActivityTestRule.getScenario().onActivity(activity -> {
            expectedLabels.add(activity.getString(R.string.collector_email));
            expectedLabels.add(activity.getString(R.string.collector_phone));
        });

        // Validar que los labels traducidos están visibles
        for (String label : expectedLabels) {
            onView(allOf(withText(label), isDisplayed()))
                    .check(matches(withText(label)));
        }

        onView(withText("Manolo Bellon")).check(matches(isDisplayed()));
        onView(withSubstring("manollo@caracol.com.co")).check(matches(isDisplayed()));
        onView(withText("3502457896")).check(matches(isDisplayed()));
    }

    @Test
    public void mainActivityTestCollectorDetailCollector() {
        configureGlobalTimeout();

        ViewInteraction guestBtn = onView(allOf(withId(R.id.btnSeeCollectorMenu), isDisplayed()));
        guestBtn.perform(click());

        ViewInteraction collectorCatalogBtn = onView(allOf(withId(R.id.btn_see_collectos), isDisplayed()));
        collectorCatalogBtn.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(withText("Manolo Bellon"), isDisplayed()))
                .perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        final List<String> expectedLabels = new ArrayList<>();
        mActivityTestRule.getScenario().onActivity(activity -> {
            expectedLabels.add(activity.getString(R.string.collector_email));
            expectedLabels.add(activity.getString(R.string.collector_phone));
        });

        // Validar que los labels traducidos están visibles
        for (String label : expectedLabels) {
            onView(allOf(withText(label), isDisplayed()))
                    .check(matches(withText(label)));
        }

        onView(withText("Manolo Bellon")).check(matches(isDisplayed()));
        onView(withSubstring("manollo@caracol.com.co")).check(matches(isDisplayed()));
        onView(withText("3502457896")).check(matches(isDisplayed()));
    }

}


