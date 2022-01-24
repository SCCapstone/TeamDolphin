package com.example.teamdolphin


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
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
class BehavioralTest1 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun behavioralTest1() {
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
                withId(R.id.field_width),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("5"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.field_height),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    7
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("3"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
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
        appCompatEditText3.perform(replaceText("Behavioral test1"), closeSoftKeyboard())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.field_projectName), withText("Behavioral test1"),
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
        appCompatEditText4.perform(pressImeActionButton())

        val materialRadioButton = onView(
            allOf(
                withId(R.id.radio_background), withText("Light"),
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

        val materialRadioButton2 = onView(
            allOf(
                withId(R.id.radio_background), withText("Light"),
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
        materialRadioButton2.perform(click())

        val materialRadioButton3 = onView(
            allOf(
                withId(R.id.radioButton), withText("Dark"),
                childAtPosition(
                    allOf(
                        withId(R.id.radioGroup),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            11
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialRadioButton3.perform(click())

        val materialRadioButton4 = onView(
            allOf(
                withId(R.id.radioButton), withText("Dark"),
                childAtPosition(
                    allOf(
                        withId(R.id.radioGroup),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            11
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialRadioButton4.perform(click())

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
                withId(R.id.button_brush),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linear),
                        2
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val appCompatImageButton2 = onView(
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
        appCompatImageButton2.perform(click())

        val appCompatImageButton3 = onView(
            allOf(
                withId(R.id.button_home),
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
        appCompatImageButton3.perform(click())
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
