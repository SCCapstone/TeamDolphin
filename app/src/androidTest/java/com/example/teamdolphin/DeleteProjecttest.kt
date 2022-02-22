package com.example.teamdolphin


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class DeleteProjecttest {

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
    fun deleteProjecttest() {
        val switch_ = onView(
            allOf(
                withId(R.id.button_toggleTheme), withText("Light"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.container_toggleThemeButton),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        switch_.perform(click())

        val materialButton = onView(
            allOf(
                withId(R.id.button), withText("New"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    10
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.field_projectName),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Cat"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.field_projectName), withText("Cat"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    8
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(pressImeActionButton())

        val materialRadioButton = onView(
            allOf(
                withId(R.id.fc_light_button), withText("Light"),
                childAtPosition(
                    allOf(
                        withId(R.id.radioGroup),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            11
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialRadioButton.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(R.id.button_create), withText("Create"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    9
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val appCompatImageButton = onView(
            allOf(
                withId(R.id.button_menu),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linear),
                        2
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val appCompatImageButton2 = onView(
            allOf(
                withId(R.id.button_pen),
                childAtPosition(
                    allOf(
                        withId(R.id.CanvasUIRow2),
                        childAtPosition(
                            withId(R.id.linear),
                            3
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        val appCompatImageButton3 = onView(
            allOf(
                withId(R.id.button_undo),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linear),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton3.perform(click())

        val appCompatImageButton4 = onView(
            allOf(
                withId(R.id.button_undo),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linear),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton4.perform(click())

        val appCompatImageButton5 = onView(
            allOf(
                withId(R.id.button_undo),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linear),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton5.perform(click())

        val appCompatImageButton6 = onView(
            allOf(
                withId(R.id.button_zoom),
                childAtPosition(
                    allOf(
                        withId(R.id.CanvasUIRow4),
                        childAtPosition(
                            withId(R.id.linear),
                            5
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton6.perform(click())

        val appCompatImageButton7 = onView(
            allOf(
                withId(R.id.button_zoom),
                childAtPosition(
                    allOf(
                        withId(R.id.CanvasUIRow4),
                        childAtPosition(
                            withId(R.id.linear),
                            5
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton7.perform(click())

        val appCompatImageButton8 = onView(
            allOf(
                withId(R.id.button_drag),
                childAtPosition(
                    allOf(
                        withId(R.id.CanvasUIRow4),
                        childAtPosition(
                            withId(R.id.linear),
                            5
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatImageButton8.perform(click())

        val appCompatImageButton9 = onView(
            allOf(
                withId(R.id.button_menu),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linear),
                        2
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton9.perform(click())

        val appCompatImageButton10 = onView(
            allOf(
                withId(R.id.button_menu),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linear),
                        2
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton10.perform(click())

        val appCompatImageButton11 = onView(
            allOf(
                withId(R.id.button_save),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linear),
                        2
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageButton11.perform(click())

        pressBack()

        val appCompatImageButton12 = onView(
            allOf(
                withId(R.id.cancel_button),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageButton12.perform(click())

        pressBack()

        pressBack()

        val materialButton3 = onView(
            allOf(
                withId(android.R.id.button1), withText("Yes"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    3
                )
            )
        )
        materialButton3.perform(scrollTo(), click())
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
