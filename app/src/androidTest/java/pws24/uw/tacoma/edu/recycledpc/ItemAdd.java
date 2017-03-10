package pws24.uw.tacoma.edu.recycledpc;

import android.os.SystemClock;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.NavigationView;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by Arthur on 3/6/2017.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest

public class ItemAdd {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule = new ActivityTestRule<>(
            HomeActivity.class);



    @Test
    public void testItemAddNameShort() {
        SystemClock.sleep(2000);

        onView(withId(R.id.fab)).perform(click());



        Random random = new Random();


        // Type text and then press the button.
        onView(withId(R.id.add_course_id))
                .perform(typeText("A"));
        onView(withId(R.id.add_course_short_desc))
                .perform(typeText("$5.00"));
        onView(withId(R.id.add_course_long_desc))
                .perform(typeText("Test Description"));
        onView(withId(R.id.add_course_button))
                .perform(click());
        onView(withText("Item name too short"))
                .inRoot(withDecorView(IsNot.not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));


    }


    @Test
    public void testItemAddPriceWrong() {
        SystemClock.sleep(2000);

        onView(withId(R.id.fab)).perform(click());



        Random random = new Random();

        String name = "Item:" + (random.nextInt(400) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1);

        // Type text and then press the button.
        onView(withId(R.id.add_course_id))
                .perform(typeText(name));
        onView(withId(R.id.add_course_short_desc))
                .perform(typeText("$$$MONEYTIME$$$"));
        onView(withId(R.id.add_course_long_desc))
                .perform(typeText("Test Description"));
        onView(withId(R.id.add_course_button))
                .perform(click());
        onView(withText("Price not valid"))
                .inRoot(withDecorView(IsNot.not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));


    }

    @Test
    public void testItemAddDescShort() {
        SystemClock.sleep(2000);

        onView(withId(R.id.fab)).perform(click());



        Random random = new Random();

        String name = "Item:" + (random.nextInt(400) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1);

        // Type text and then press the button.
        onView(withId(R.id.add_course_id))
                .perform(typeText(name));
        onView(withId(R.id.add_course_short_desc))
                .perform(typeText("$5.00"));
        onView(withId(R.id.add_course_long_desc))
                .perform(typeText("T"));
        onView(withId(R.id.add_course_button))
                .perform(click());
        onView(withText("Description too short"))
                .inRoot(withDecorView(IsNot.not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));


    }

    @Test
    public void testItemAdd() {
        SystemClock.sleep(2000);

        onView(withId(R.id.fab)).perform(click());



        Random random = new Random();

        String name = "Item:" + (random.nextInt(400) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1);

        // Type text and then press the button.
        onView(withId(R.id.add_course_id))
                .perform(typeText(name));
        onView(withId(R.id.add_course_short_desc))
                .perform(typeText("$5.00"));
        onView(withId(R.id.add_course_long_desc))
                .perform(typeText("This is a description"));
        onView(withId(R.id.add_course_button))
                .perform(click());
        onView(withText("Item successfully added!"))
                .inRoot(withDecorView(IsNot.not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));




    }


}
