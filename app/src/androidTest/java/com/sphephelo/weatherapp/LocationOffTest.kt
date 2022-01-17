package com.sphephelo.weatherapp

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId


@RunWith(JUnit4::class)
class LocationOffTest
{
    lateinit var scenario:ActivityScenario<LocationOff>

    @get:Rule
    var rule = ActivityTestRule(LocationOff::class.java)



    @Before
    fun setUp()
    {
        scenario= ActivityScenario.launch(LocationOff::class.java);
        scenario.moveToState(Lifecycle.State.RESUMED);
    }
    @Test
    fun BtnClicked()
    {
        onView(withId(R.id.allow_permission)).perform(click() );
    }

}