package com.masharo.habits.screens.habit

import android.app.Application
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import com.masharo.habits.data.habit.Habit
import com.masharo.habits.data.HabitDatabase
import com.masharo.habits.databinding.FragmentHabitBinding
import com.masharo.habits.data.habit.DataLogicHabit
import com.masharo.habits.data.habit.RoomHabitDataLogic

class HabitViewModel(private val app: Application) : AndroidViewModel(app) {

    //инициализировать при старте
    private lateinit var bind: FragmentHabitBinding
    private var id: Int? = null
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var dataLogic: DataLogicHabit
    private lateinit var fragmentManager: FragmentManager

    private lateinit var habit: Habit
    private var isNewHabit: Boolean = true
    private var isInstance: Boolean = false

    fun instance(bind: FragmentHabitBinding,
                 id: Int?,
                 resultLauncher: ActivityResultLauncher<Intent>,
                 childFragmentManager: FragmentManager
    ) {
        this.bind = bind
        this.id = id
        this.resultLauncher = resultLauncher
        this.fragmentManager = childFragmentManager

        dataLogic = RoomHabitDataLogic(HabitDatabase.instance(app.applicationContext))

        val habitOrNull = dataLogic.getHabit(id)

        if (!isInstance) {
            isInstance = true

            isNewHabit = when (habitOrNull) {
                null -> {
                    habit = Habit()
                    true
                }
                else -> {
                    habit = habitOrNull
                    false
                }
            }
        }

        bind.viewModel = this
        bind.isNewHabit = isNewHabit
        bind.habit = habit

    }

    fun onClickStartColorPicker() {
        val colorPickerFragment: ColorPickerFragment = habit.color?.let {
            ColorPickerFragment.newInstance(it)
        } ?:
            ColorPickerFragment()

        colorPickerFragment.show(fragmentManager, ColorPickerFragment.TAG)
    }

    fun onClickButtonSave() {

        if (isNewHabit) {
            dataLogic.addHabit(habit)
        } else {
            dataLogic.setHabit(habit)
        }

    }

    fun doneInc() {
        habit.countDone++
    }

    fun doneDec() {
        if (habit.countDone > 0) {
            habit.countDone--
        }
    }

    fun changeHabitColor(color: Int) {
        habit.color = color
    }

}