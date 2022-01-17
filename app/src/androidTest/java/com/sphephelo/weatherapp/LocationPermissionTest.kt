package com.sphephelo.weatherapp

import android.Manifest
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.JUnitCore
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId

@RunWith(JUnit4::class)
class LocationPermissionTest
{
    @get:Rule
    val rule=ActivityTestRule(LocationPermission::class.java);

    lateinit var scenario:ActivityScenario<LocationPermission>;

    @Before()
    fun SetTup()
    {
        scenario= ActivityScenario.launch(LocationPermission::class.java)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }
    //Test the permissions function
    @Test fun Permissions()
    {
        rule.activity.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION,1);


    }
    //test the function
    @Test
    fun btnTest()
    {
        onView(withId(R.id.allow_permission)).perform(click());
    }
    
}