package incorporate.games.avon.trynefaktorapp;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.squareup.javawriter.JavaWriter.type;


import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class QuizActivityTest extends AppCompatActivity {

    List<Player> playerList;

    @Rule
    public ActivityTestRule<QuizActivity> mActivityTestRule = new ActivityTestRule<QuizActivity>(QuizActivity.class);

    private String guess = "lil";
    @Before
    public void setUp() throws Exception {
        playerList = ((PlayerList) getApplication()).getList();
    }
    @Test
    public void testUserInput(){
        //input some text in the edit text
        Espresso.onView(withId(R.id.guessInput)).perform(typeText(guess));
        //close soft keyboard
         Espresso.closeSoftKeyboard();
        //perform button click
        Espresso.onView(withId(R.id.testAnswer)).perform(click());
        //checking the text
        //Espresso.onData(playerList.get(0).getName()).check(matches(withText(guess)));
    }

    @After
    public void tearDown() throws Exception {
    }
}