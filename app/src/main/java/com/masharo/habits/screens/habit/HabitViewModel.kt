package com.masharo.habits.screens.habit

import android.app.Application
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.masharo.habits.data.habit.Habit
import com.masharo.habits.data.HabitDatabase
import com.masharo.habits.databinding.FragmentHabitBinding
import com.masharo.habits.data.habit.RoomHabitDataLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class HabitViewModel(private val app: Application) : AndroidViewModel(app) {

    //инициализировать при старте
    private lateinit var bind: FragmentHabitBinding
    private var id: Int? = null
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val dataLogic = RoomHabitDataLogic(HabitDatabase.instance(app.applicationContext))
    private lateinit var fragmentManager: FragmentManager

    private val habitLocal: MutableLiveData<Habit> = MutableLiveData(Habit())
    val habit = habitLocal
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

        this.bind.lifecycleOwner

        if (!isInstance) {
            isInstance = true

            viewModelScope.launch(Dispatchers.IO) {
                dataLogic.getHabit(id)?.let {
                    isNewHabit = false
                    habitLocal.postValue(it)
                }
            }
        }

        bind.isNewHabit = isNewHabit
        bind.viewModel = this
    }

    fun onClickStartColorPicker() {
        val colorPickerFragment: ColorPickerFragment = habitLocal.value?.color?.let {
            ColorPickerFragment.newInstance(it)
        } ?:
            ColorPickerFragment()

        colorPickerFragment.show(fragmentManager, ColorPickerFragment.TAG)
    }

    fun onClickButtonSave() = viewModelScope.launch(Dispatchers.IO) {
        habitLocal.value?.let {
            if (isNewHabit) {
                dataLogic.addHabit(it)
            } else {
                dataLogic.setHabit(it)
            }
        }
    }


    fun doneInc() {
        habitLocal.value?.apply {
            countDone++
        }
    }

    fun doneDec() {
        habitLocal.value?.apply {
            if (countDone > 0) {
                countDone--
            }
        }
    }

    fun changeHabitColor(color: Int) {
        habit.value?.let {
            it.color = color
        }
    }
}