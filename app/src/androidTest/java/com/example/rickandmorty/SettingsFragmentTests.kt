package com.example.rickandmorty

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.rickandmorty.ui.settings.SettingsFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SettingsFragmentTests {
    private lateinit var stringToBetyped: String

    @Before
    fun initValidString() {
        stringToBetyped = "espresso"
    }

    @Test
    fun typeEditText_matchesSameValue() {
        launchFragmentInContainer<SettingsFragment>()

        onView(withId(R.id.editTextSettings))
            .perform(typeText(stringToBetyped), closeSoftKeyboard())

        onView(withId(R.id.editTextSettings))
            .check(matches(withText("espresso")))
    }

    @Test
    fun clickRadioButtonDark_radioButtonDarkIsChecked() {
        launchFragmentInContainer<SettingsFragment>()

        onView(withId(R.id.radio_dark)).perform(click())
        //TODO: verify snackbar after clicking

        onView(withId(R.id.radio_dark)).check(matches(isDisplayed()))
        onView(withId(R.id.radio_dark)).check(matches(isChecked()))
        onView(withId(R.id.radio_light)).check(matches(isNotChecked()))
    }
}