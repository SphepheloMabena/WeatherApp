package com.sphephelo.weatherapp



import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.sphephelo.weatherapp.MainActivity
import androidx.test.rule.ActivityTestRule






@RunWith(JUnit4::class)
class MainActivityTest {
    lateinit var scenario: ActivityScenario<MainActivity> ;


    @get:Rule
    var rule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun Setup()
    {
        scenario=ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED);

    }

    @Test
    fun SearchTest() {


        //tests the search buttom whether it works well
        onView(withId(R.id.search))
            .perform(click())



    }
    @Test
    fun ForecastTest()
    {
        val activity = rule.activity
        activity.startForecast("https://api.openweathermap.org/data/2.5/forecast?lat=${latitude}&lon=${longitude}&appid=94b7c8421c8f5599bc09de7cdd301ca1");

    }
    @Test
    fun CurrentWeatherTest()
    {
        val activity = rule.activity
        activity.getCurrentWeather("https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&appid=94b7c8421c8f5599bc09de7cdd301ca1");
    }


}
