package com.dicoding.courseschedule.ui.home.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.dicoding.courseschedule.ui.home.HomeActivity
import com.dicoding.courseschedule.R
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun clickFloatingActionButtonAndShouldNavigateToAddTaskActivity() {
        onView(withId(R.id.action_add)).perform(click())
    }

}