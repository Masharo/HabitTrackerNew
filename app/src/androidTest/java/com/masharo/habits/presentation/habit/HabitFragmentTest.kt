package com.masharo.habits.presentation.habit

import androidx.test.rule.ActivityTestRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.masharo.habits.R
import com.masharo.habits.presentation.root.RootHabitActivity
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HabitFragmentTest : TestCase() {

    @get:Rule
    val activity = ActivityTestRule(RootHabitActivity::class.java)

    @Test
    fun myClassMethod_ReturnsTrue() {
        onView(withId(R.id.fab_add_habit)).perform(click()).check(matches(isDisplayed()))
        assert(true)
    }

}

