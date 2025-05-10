package com.example.vynilsapp;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.IdlingPolicies;

import com.example.vynilsapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestVynilsE2EHU01 {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    public void configureGlobalTimeout() {
        // Configurar tiempo de espera para operaciones asíncronas
        IdlingPolicies.setIdlingResourceTimeout(3, TimeUnit.SECONDS);

        // Configurar tiempo de espera para interacciones con la UI
        IdlingPolicies.setMasterPolicyTimeout(3, TimeUnit.SECONDS);
    }

    @Test
    public void mainActivityTestGuest() {
        configureGlobalTimeout();

        ViewInteraction guestBtn = onView(allOf(withId(R.id.btnSeeGuestMenu), withText("Invitado"), isDisplayed()));
        guestBtn.perform(click());

        ViewInteraction albumsCatalogBtn = onView(allOf(withId(R.id.btn_see_album_catalog), withText("Ver Catálogo de Álbumes"), isDisplayed()));
        albumsCatalogBtn.perform(click());

        onView(withId(R.id.btnCreateAlbum)).check(matches(not(isDisplayed())));

        String[] expectedAlbums = {"Buscando América", "Poeta del pueblo", "A Night at the Opera", "A Day at the Races"};

        for (String albumName : expectedAlbums) {
            onView(allOf(withText(albumName), isDisplayed()))
                    .check(matches(withText(albumName)));
        }

    }
    @Test
    public void mainActivityTestCollector() {
        configureGlobalTimeout();

        ViewInteraction guestBtn = onView(allOf(withId(R.id.btnSeeCollectorMenu), withText("Coleccionista"), isDisplayed()));
        guestBtn.perform(click());

        ViewInteraction albumsCatalogBtn = onView(allOf(withId(R.id.btn_see_album_catalog), withText("Ver Catálogo de Álbumes"), isDisplayed()));
        albumsCatalogBtn.perform(click());

        onView(withId(R.id.btnCreateAlbum)).check(matches(isDisplayed()));

        String[] expectedAlbums = {"Buscando América", "Poeta del pueblo", "A Night at the Opera", "A Day at the Races"};

        for (String albumName : expectedAlbums) {
            onView(allOf(withText(albumName), isDisplayed()))
                    .check(matches(withText(albumName)));
        }

    }

}


