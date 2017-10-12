package piftik.github.com.weatherproject.view;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import piftik.github.com.weatherproject.R;
import piftik.github.com.weatherproject.WeatherListMain;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class WeatherListMainTest {

    @Rule
    public ActivityTestRule<WeatherListMain> mActivityTestRule = new ActivityTestRule<>(WeatherListMain.class);

    @Test
    public void weatherListMainTest() {


        String[] myArray =
                mActivityTestRule.getActivity().getResources()
                        .getStringArray(R.array.city);

        for (String aMyArray : myArray) {
            ViewInteraction spinner = onView(
                    allOf(withId(R.id.spiner_city), isDisplayed()));
            spinner.perform(click());
            onData(is(aMyArray)).perform(click());
        }


        ViewInteraction button = onView(
                allOf(withId(R.id.button_get_wether), withText("Get The Weather"), isDisplayed()));
        button.perform(click());

    }


}
