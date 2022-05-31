package com.masharo.habits.customize

import android.view.View
import android.widget.HorizontalScrollView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers

object HorizontalScroll {

    fun scrollTo(position: Int) = object : ViewAction {

            override fun getConstraints() =
                Matchers.allOf(
                    ViewMatchers.isAssignableFrom(HorizontalScrollView::class.java),
                    ViewMatchers.isDisplayed()
                )

            override fun getDescription() =
                "Scroll HorizontalScrollView to $position X position"

            override fun perform(uiController: UiController?, view: View?) {
                if (view is HorizontalScrollView) {
                    view.scrollTo(position, 0)
                }
            }

        }


    fun scrollToEnd() = object : ViewAction {

            override fun getConstraints() =
                Matchers.allOf(
                    ViewMatchers.isAssignableFrom(HorizontalScrollView::class.java),
                    ViewMatchers.isDisplayed()
                )

            override fun getDescription() =
                "Scroll HorizontalScrollView to end X position"

            override fun perform(uiController: UiController?, view: View?) {
                if (view is HorizontalScrollView) {
                    view.fullScroll(View.FOCUS_RIGHT)
                }
            }

        }


}