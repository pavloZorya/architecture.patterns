package com.arcchitecturepatterns

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @Rule
    @JvmField
    var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java
    )

    @Test
    fun testClickOnButtonOpensNewScreen() {
        onView(withId(R.id.mvvmButton)).perform(click())
    }

    @Test
    fun testClickOnButtonOpensMvvmActivity() {
        onView(withId(R.id.mvvmButton)).perform(click())
        onView(withText("MVVM")).check(matches(isDisplayed()))
    }
}