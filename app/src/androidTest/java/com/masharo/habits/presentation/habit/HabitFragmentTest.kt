package com.masharo.habits.presentation.habit

import android.Manifest
import android.widget.LinearLayout
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.kaspresso.screens.KScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.masharo.habits.R
import com.masharo.habits.customize.HorizontalScroll
import com.masharo.habits.presentation.root.RootHabitActivity
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.scroll.KScrollView
import io.github.kakaocup.kakao.spinner.KSpinner
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.junit.Rule
import org.junit.Test

class HabitFragmentTest : TestCase() {

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    object HabitListScreen : KScreen<HabitListScreen>() {
        override val layoutId: Int = R.layout.activity_root_habit
        override val viewClass: Class<*> = RootHabitActivity::class.java

        val fabAdd = KButton { withId(R.id.fab_add_habit) }

    }

    object HabitScreen : KScreen<HabitScreen>() {
        override val layoutId: Int = R.layout.activity_root_habit
        override val viewClass: Class<*> = RootHabitActivity::class.java

        val butSave = KButton { withId(R.id.button_habit_save) }
        val butSelectColor = KButton { withId(R.id.button_habit_colorPicker) }

        val tvTitle = KTextView { withId(R.id.textView_habit_title) }
        val tvType = KTextView { withId(R.id.textView_habit_type) }
        val tvTitleCount = KTextView { withId(R.id.textView_habit_count) }
        val tvDone = KTextView { withId(R.id.editText_habit_done) }
        val tvTitleDone = KTextView { withId(R.id.textView_habit_done) }

        val etTitle = KEditText { withId(R.id.editText_habit_title) }
        val etDescription = KEditText { withId(R.id.editText_habit_description) }
        val etCount = KEditText { withId(R.id.editText_habit_count) }

        val spinPriority = KSpinner({ withId(R.id.spinner_habit_priority) }, {})
        val spinPeriod = KSpinner({ withId(R.id.spinner_habit_period) }, {})

    }

    object ColorPickerScreen : KScreen<ColorPickerScreen>() {
        override val layoutId: Int = R.layout.activity_root_habit
        override val viewClass: Class<*> = RootHabitActivity::class.java

        val scrollColors = KScrollView { withId(R.id.scrollView_colorPicker_colors) }
    }

    @get:Rule
    val activityTestRule = ActivityTestRule(RootHabitActivity::class.java, true, false)

    @Test
    fun test() =
        run {
            step("Открыть экран создания") {
                activityTestRule.launchActivity(null)
                HabitListScreen {
                    fabAdd {
                        isVisible()
                        click()
                    }
                }
            }

            step("Проверить наличие View") {
                HabitScreen {
                    butSave {
                        isVisible()
                    }
                    tvTitle {
                        isVisible()
                    }
                    tvType {
                        isVisible()
                    }
                    tvTitleCount {
                        isVisible()
                    }
                    tvDone {
                        isVisible()
                        containsText("")
                        hasHint("0")
                    }
                    butSelectColor {
                        isVisible()
                    }
                    tvTitleDone {
                        isVisible()
                    }
                    etCount {
                        isVisible()
                        containsText("")
                        hasHint("0")
                    }
                    etTitle {
                        isVisible()
                    }
                    etDescription {
                        isVisible()
                    }
                    spinPriority {
                        isVisible()
                    }
                    spinPeriod {
                        isVisible()
                    }
                }
            }

            step("Тест ColorPicker") {
                HabitScreen {
                    butSelectColor {
                        click()
                    }
                }
                ColorPickerScreen {
                    scrollColors {
                        isVisible()
                        view.perform(HorizontalScroll.scrollToEnd())
                    }
                }
            }


        }

}