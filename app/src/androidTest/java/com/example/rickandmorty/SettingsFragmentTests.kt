package com.example.rickandmorty

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
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
}