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

import java.util.concurrent.TimeUnit;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestVynilsE2EHU02 {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    public void configureGlobalTimeout() {
        // Configurar tiempo de espera para operaciones asíncronas
        IdlingPolicies.setIdlingResourceTimeout(8, TimeUnit.SECONDS);

        // Configurar tiempo de espera para interacciones con la UI
        IdlingPolicies.setMasterPolicyTimeout(8, TimeUnit.SECONDS);
    }

    @Test
    public void mainActivityTestAlbumDetailGuest() {
        configureGlobalTimeout();

        ViewInteraction guestBtn = onView(allOf(withId(R.id.btnSeeGuestMenu), withText("Invitado"), isDisplayed()));
        guestBtn.perform(click());

        ViewInteraction albumsCatalogBtn = onView(allOf(withId(R.id.btn_see_album_catalog), withText("Ver Catálogo de Álbumes"), isDisplayed()));
        albumsCatalogBtn.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(withText("Buscando América"), isDisplayed()))
                .perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] expectedLabels = {"Lanzamiento", "Género", "Sello discográfico", "Descripción"};

        for (String label : expectedLabels) {
            onView(allOf(withText(label), isDisplayed()))
                    .check(matches(withText(label)));
        }

        onView(withText("1984-08-01")).check(matches(isDisplayed()));
        onView(withSubstring("Buscando América es el primer álbum de la banda")).check(matches(isDisplayed()));
        onView(withText("Salsa")).check(matches(isDisplayed()));
        onView(withText("Elektra")).check(matches(isDisplayed()));

    }
    @Test
    public void mainActivityTestAlbumDetailCollector() {
        configureGlobalTimeout();

        ViewInteraction guestBtn = onView(allOf(withId(R.id.btnSeeCollectorMenu), withText("Coleccionista"), isDisplayed()));
        guestBtn.perform(click());

        ViewInteraction albumsCatalogBtn = onView(allOf(withId(R.id.btn_see_album_catalog), withText("Ver Catálogo de Álbumes"), isDisplayed()));
        albumsCatalogBtn.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(withText("Buscando América"), isDisplayed()))
                .perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] expectedLabels = {"Lanzamiento", "Género", "Sello discográfico", "Descripción"};

        for (String label : expectedLabels) {
            onView(allOf(withText(label), isDisplayed()))
                    .check(matches(withText(label)));
        }

        onView(withText("1984-08-01")).check(matches(isDisplayed()));
        onView(withSubstring("Buscando América es el primer álbum de la banda")).check(matches(isDisplayed()));
        onView(withText("Salsa")).check(matches(isDisplayed()));
        onView(withText("Elektra")).check(matches(isDisplayed()));

    }

}


