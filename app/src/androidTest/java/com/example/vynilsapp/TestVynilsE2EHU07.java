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
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.vynilsapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestVynilsE2EHU07 {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTestCollector() {

        ViewInteraction guestBtn = onView(allOf(withId(R.id.btnSeeCollectorMenu), withText("Coleccionista"), isDisplayed()));
        guestBtn.perform(click());

        ViewInteraction albumsCatalogBtn = onView(allOf(withId(R.id.btn_see_album_catalog), withText("Ver Catálogo de Álbumes"), isDisplayed()));
        albumsCatalogBtn.perform(click());

        String[] expectedAlbums = {"Buscando América", "Poeta del pueblo", "A Night at the Opera", "A Day at the Races"};

        for (String albumName : expectedAlbums) {
            onView(allOf(withText(albumName), isDisplayed()))
                    .check(matches(withText(albumName)));
        }

        onView(withId(R.id.btnCreateAlbum)).check(matches(isDisplayed()));

        ViewInteraction createAlbumBtn = onView(allOf(withId(R.id.btnCreateAlbum), withText("CREAR ALBUM"), isDisplayed()));
        createAlbumBtn.perform(click());

        onView(withId(R.id.etName)).check(matches(isDisplayed()));
        onView(withId(R.id.etCover)).check(matches(isDisplayed()));
        onView(withId(R.id.etReleaseDate)).check(matches(isDisplayed()));
        onView(withId(R.id.etDescription)).check(matches(isDisplayed()));
        onView(withId(R.id.actvGenre)).check(matches(isDisplayed()));
        onView(withId(R.id.actvRecordLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.btnSaveAlbum)).check(matches(isDisplayed()));

        ViewInteraction albumNameTxt = onView(withId(R.id.etName));
        albumNameTxt.perform(scrollTo(), replaceText("Reflections"), closeSoftKeyboard());

        ViewInteraction albumCoverTxt = onView(withId(R.id.etCover));
        albumCoverTxt.perform(scrollTo(), replaceText("https://www.ritmo.es/Portals/0/EasyDNNnews/4596/img-AC202102_Foto12A.jpg"), closeSoftKeyboard());

        ViewInteraction albumReleaseDateTxt = onView(withId(R.id.etReleaseDate));
        albumReleaseDateTxt.perform(scrollTo(), replaceText("2021-05-14T00:00:00-05:00"), closeSoftKeyboard());

        ViewInteraction albumDescription = onView(withId(R.id.etDescription));
        albumDescription.perform(scrollTo(), replaceText("Una colección de obras clásicas interpretadas magistralmente por el violonchelista Pablo Ferrández."), closeSoftKeyboard());

        ViewInteraction albumGenreTxt = onView(withId(R.id.actvGenre));
        albumGenreTxt.perform(scrollTo(), replaceText("Classical"), closeSoftKeyboard());

        ViewInteraction albumRecordLabelTxt = onView(withId(R.id.actvRecordLabel));
        albumRecordLabelTxt.perform(scrollTo(), replaceText("Sony Music"), closeSoftKeyboard());

        ViewInteraction saveAlbumBtn = onView(allOf(withId(R.id.btnSaveAlbum), withText("Guardar álbum"), isDisplayed()));
        saveAlbumBtn.perform(click());

    }

    @Test
    public void mainActivityTestCollectorVal() {

        ViewInteraction guestBtn = onView(allOf(withId(R.id.btnSeeCollectorMenu), withText("Coleccionista"), isDisplayed()));
        guestBtn.perform(click());

        ViewInteraction albumsCatalogBtn = onView(allOf(withId(R.id.btn_see_album_catalog), withText("Ver Catálogo de Álbumes"), isDisplayed()));
        albumsCatalogBtn.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(withText("Reflections"), isDisplayed())).check(matches(withText("Reflections")));

    }

}

