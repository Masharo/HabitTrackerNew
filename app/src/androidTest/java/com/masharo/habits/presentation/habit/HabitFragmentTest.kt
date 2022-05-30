package com.masharo.habits.presentation.habit

import android.Manifest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.kaspresso.screens.KScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.masharo.habits.R
import com.masharo.habits.presentation.root.RootHabitActivity
import io.github.kakaocup.kakao.text.KButton
import org.junit.Rule
import org.junit.Test

class HabitFragmentTest : TestCase() {

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    object MainScreen : KScreen<MainScreen>() {
        override val layoutId: Int = R.layout.activity_root_habit
        override val viewClass: Class<*> = RootHabitActivity::class.java

        val fabAdd = KButton { withId(R.id.fab_add_habit) }

    }

    @get:Rule
    val activityTestRule = ActivityTestRule(RootHabitActivity::class.java, true, false)

    @Test
    fun test() =
        run {
            step("Open Simple Screen") {
                activityTestRule.launchActivity(null)
                testLogger.i("I am testLogger")
                device.screenshots.take("Additional_screenshot")
                MainScreen {
                    fabAdd {
                        isVisible()
                        click()
                    }
                }
            }
        }

}