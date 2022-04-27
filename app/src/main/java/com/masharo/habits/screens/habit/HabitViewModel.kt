package com.masharo.habits.screens.habit

import android.app.Application
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.masharo.habits.data.habit.Habit
import com.masharo.habits.data.HabitDatabase
import com.masharo.habits.databinding.FragmentHabitBinding
import com.masharo.habits.data.habit.RoomHabitDataLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitViewModel(private val app: Application) : AndroidViewModel(app) {

    //инициализировать при старте
    private lateinit var bind: FragmentHabitBinding
    private var id: Int? = null
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val dataLogic = RoomHabitDataLogic(HabitDatabase.instance(app.applicationContext))
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

        if (!isInstance) {
            isInstance = true

            viewModelScope.launch(Dispatchers.IO) {

                isNewHabit = when (
                    val habitLocal = dataLogic.getHabit(id)
                ) {
                    null -> {
                        habit = Habit()
                        true
                    }
                    else -> {
                        habit = habitLocal
                        false
                    }
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

    fun onClickButtonSave() = viewModelScope.launch(Dispatchers.IO) {
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