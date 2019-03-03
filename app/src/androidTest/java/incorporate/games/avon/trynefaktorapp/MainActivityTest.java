package incorporate.games.avon.trynefaktorapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =new ActivityTestRule<MainActivity>(MainActivity.class);

    private String owner1 = "sindre";
    private String owner2 = "Owner: sindre";

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testUserInput(){
        Espresso.openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
//  onView(withId(R.id.pref)).perform(click());
        Espresso.onView(withText(R.string.app_name)).perform(click());

        onView(withHint("name")).inRoot(isDialog()) // <---
                .check(matches(isDisplayed()))
                .perform(typeText(owner1));

        onView(withText("Ok")).inRoot(isDialog()) // <---
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.owner)).check(matches(withText(owner2)));
    }

    @After
    public void tearDown() throws Exception {

    }
}