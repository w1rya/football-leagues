package com.wiryatech.footballleagues.ui.activities

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.wiryatech.footballleagues.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class TeamActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SplashscreenActivity::class.java)

    @Test
    fun teamActivityTest() {
        Thread.sleep(3000)
        val recyclerView = onView(
            allOf(
                withId(R.id.rv_league),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    2
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(4, click()))

        Thread.sleep(3000)
        val tabView = onView(
            allOf(
                withContentDescription("TEAMS"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tab_detail),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        tabView.perform(click())

        Thread.sleep(3000)
        val recyclerView2 = onView(
            allOf(
                withId(R.id.rv_teams),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    0
                )
            )
        )
        recyclerView2.perform(scrollToPosition<ViewHolder>(14))
        Thread.sleep(2000)
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(14, click()))

        Thread.sleep(3000)
        onView(withId(R.id.btn_fav)).check(ViewAssertions.matches(isDisplayed())).perform(click())

        Thread.sleep(2000)
        pressBack()
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
