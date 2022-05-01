package com.masharo.habits.presentation.habit

import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.*
import com.masharo.habits.data.HabitDatabase
import com.masharo.habits.data.habit.Habit
import com.masharo.habits.data.habit.RoomHabitDataLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitViewModel(
    context: Context,
    private val habitId: Int?
): ViewModel(), Observable {

//    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
//    private lateinit var fragmentManager: FragmentManager

    private val dataLogic = RoomHabitDataLogic(HabitDatabase.instance(context))
    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()
    private val habitLocal: MutableLiveData<Habit> = MutableLiveData(Habit())

    @Bindable
    val habit: LiveData<Habit> = habitLocal

    init {
        viewModelScope.launch(Dispatchers.IO) {
            habitId?.let { id ->
                dataLogic.getHabit(id)?.let {
                    habitLocal.postValue(it)
                }
            }
        }
    }

    fun onClickStartColorPicker() {
//        val colorPickerFragment: ColorPickerFragment = habitLocal.value?.color?.let {
//            ColorPickerFragment.newInstance(it)
//        } ?:
//            ColorPickerFragment()
//
//        colorPickerFragment.show(fragmentManager, ColorPickerFragment.TAG)
    }

    fun save() = viewModelScope.launch(Dispatchers.IO) {
        habitLocal.value?.apply {
            id?.let {
                dataLogic.setHabit(this)
            } ?: dataLogic.addHabit(this)
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

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }
}

//class FactoryHabitViewModel(val context: Context, val id: Int?) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return HabitViewModel(context, id) as T
//    }
//}