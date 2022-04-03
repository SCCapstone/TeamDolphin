package com.example.teamdolphin


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class Eyedroppertest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.READ_EXTERNAL_STORAGE"
        )

    @Test
    fun eyedroppertest() {
        val linearLayout = onData(anything())
            .inAdapterView(
                allOf(
                    withId(R.id.homepage_gridView),
                    childAtPosition(
                        withId(R.id.linearLayout),
                        0
                    )
                )
            )
            .atPosition(2)
        linearLayout.perform(click())

        val appCompatImageButton = onView(
            allOf(
                withId(R.id.button_menu),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val appCompatImageButton2 = onView(
            allOf(
                withId(R.id.button_eyedropper),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        val appCompatImageButton3 = onView(
            allOf(
                withId(R.id.button_menu),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton3.perform(click())

        val appCompatImageButton4 = onView(
            allOf(
                withId(R.id.button_paintbucket),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        appCompatImageButton4.perform(click())

        val appCompatImageButton5 = onView(
            allOf(
                withId(R.id.button_primary),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton5.perform(click())

        val appCompatImageButton6 = onView(
            allOf(
                withId(R.id.button_primary),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton6.perform(click())

        val appCompatImageButton7 = onView(
            allOf(
                withId(R.id.button_menu),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton7.perform(click())

        val appCompatImageButton8 = onView(
            allOf(
                withId(R.id.button_paintbucket),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        appCompatImageButton8.perform(click())

        val appCompatImageButton9 = onView(
            allOf(
                withId(R.id.button_color),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton9.perform(click())

        val materialButton = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton.perform(scrollTo(), click())

        val appCompatImageButton10 = onView(
            allOf(
                withId(R.id.button_primary),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton10.perform(click())

        val appCompatImageButton11 = onView(
            allOf(
                withId(R.id.button_menu),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton11.perform(click())

        val appCompatImageButton12 = onView(
            allOf(
                withId(R.id.button_menu),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton12.perform(click())

        val appCompatImageButton13 = onView(
            allOf(
                withId(R.id.button_menu),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton13.perform(click())

        val appCompatImageButton14 = onView(
            allOf(
                withId(R.id.button_brush),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatImageButton14.perform(click())

        val appCompatImageButton15 = onView(
            allOf(
                withId(R.id.button_menu),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton15.perform(click())

        val appCompatImageButton16 = onView(
            allOf(
                withId(R.id.button_menu),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton16.perform(click())

        val appCompatImageButton17 = onView(
            allOf(
                withId(R.id.button_color),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton17.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(android.R.id.button1), withText("OK"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton2.perform(scrollTo(), click())

        val appCompatImageButton18 = onView(
            allOf(
                withId(R.id.button_menu),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton18.perform(click())

        val appCompatImageButton19 = onView(
            allOf(
                withId(R.id.button_menu),
                childAtPosition(
                    allOf(
                        withId(R.id.tools_grid_layout),
                        childAtPosition(
                            withId(R.id.linear),
                            2
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton19.perform(click())
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
