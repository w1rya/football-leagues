package com.wiryatech.footballleagues.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.wiryatech.footballleagues.R.id.*
import com.wiryatech.footballleagues.ui.activities.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SearchTest {

    private val team = "man united"

    @Rule
    @JvmField var searchRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun searchScenario() {
        // mengecek apakah bottom navigation bar dengan id bottomBar tampil, lalu klik pada navigation bar
        onView(withId(bottomBar)).check(matches(isDisplayed())).perform(click())

        // melakukan pengecekan apakah searchview tampil, lalu mengetik nama tim yang ingin dicari
        onView(withId(search)).check(matches(isDisplayed()))
        onView(
            Matchers.allOf(
                withId(search_src_text),
                childAtPosition(
                    Matchers.allOf(
                        withId(search_plate),
                        childAtPosition(
                            withId(search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        ).perform(typeText(team), closeSoftKeyboard())

        // mengecek teks yang dimasukkan lalu meng-klik tombol pencarian
        onView(
            Matchers.allOf(
                withId(search_src_text), ViewMatchers.withText(team),
                childAtPosition(
                    Matchers.allOf(
                        withId(search_plate),
                        childAtPosition(
                            withId(search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        ).perform(pressImeActionButton())

        /*
            memberikan jeda 6 detik untuk meload data lalu melakukan klik pada item ke-1 (indeks 0)
            setelah diklik maka akan masuk ke halaman detail match
         */
        Thread.sleep(5000)

        val recyclerView = onView(
            Matchers.allOf(
                withId(rv_match),
                childAtPosition(
                    ViewMatchers.withClassName(Matchers.`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    2
                )
            )
        )
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        Thread.sleep(5000)

        // mengecek tombol favorit dan mengkliknya, baik menyimpan (jika belum disave) ataupun menghapus (jika sudah disave sebelumnya)
        onView(withId(btn_fav)).check(matches(isDisplayed())).perform(click())

        // memberikan jeda 3 detik untuk melihat snackbar tampil dan memastikan dengan mata sendiri bahwa tombol favoritnya benar telah diklik
        Thread.sleep(3000)

        // kembali ke hasil pencarian dengan tombol imagebutton back
        val appCompatImageButton = onView(
            Matchers.allOf(
                withId(btn_back), ViewMatchers.withContentDescription("Back"),
                childAtPosition(
                    childAtPosition(
                        withId(swipeRefresh),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        // memberikan jeda untuk mengamati testing
        Thread.sleep(3000)

        // kembali ke home
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