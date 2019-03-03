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
public class QuizActivityTest  {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private String guess = "lil";
    private String score1 = "Score: 1/1";
    private String score2 = "Score: 1/2";

    @Before
    public void setUp(){

    }
    @Test
    public void testQuizInput(){
        //input some text in the edit text
        Espresso.onView(withId(R.id.goPlayers)).perform(click());
        Espresso.onView(withId(R.id.goQuiz)).perform(click());


        Espresso.onView(withId(R.id.guessInput)).perform(typeText(guess));
        //close soft keyboard
         Espresso.closeSoftKeyboard();
        //perform button click
        Espresso.onView(withId(R.id.testAnswer)).perform(click());
        //checking the text
        Espresso.onView(withId(R.id.scoreText)).check(matches(withText(score1)));
        //Guesses one more time(wrong) and tests if score is still correct
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.testAnswer)).perform(click());
        Espresso.onView(withId(R.id.scoreText)).check(matches(withText(score2)));
    }

    @After
    public void tearDown(){
    }
}