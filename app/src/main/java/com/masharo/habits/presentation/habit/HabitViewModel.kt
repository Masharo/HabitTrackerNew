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
    private val id: Int?
): ViewModel(), Observable {

//    private lateinit var resultLauncher: ActivityResultLauncher<Intent> TODO()
//    private lateinit var fragmentManager: FragmentManager TODO()

    private val dataLogic = RoomHabitDataLogic(HabitDatabase.instance(context))
    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()
    private val habitLocal: MutableLiveData<Habit> = MutableLiveData(Habit())

    @Bindable
    val habit: LiveData<Habit> = habitLocal
    private var isNewHabit: Boolean = true
    private var isInstance: Boolean = false

    init {
        if (!isInstance) {
            isInstance = true

            viewModelScope.launch(Dispatchers.IO) {
                habitLocal.postValue(
                    dataLogic.getHabit(id)?.let {
                        isNewHabit = false
                        it
                    }
                )
            }
        }
    }



//    fun instance(bind: FragmentHabitBinding,
//                 id: Int?,
//                 resultLauncher: ActivityResultLauncher<Intent>,
//                 childFragmentManager: FragmentManager
//    ) {
//        this.bind = bind
//        this.id = id
//        this.resultLauncher = resultLauncher
//        this.fragmentManager = childFragmentManager
//
//        this.bind.lifecycleOwner
//
//        if (!isInstance) {
//            isInstance = true
//
//            viewModelScope.launch(Dispatchers.IO) {
//                habitLocal.postValue(
//                    dataLogic.getHabit(id)?.let {
//                        isNewHabit = false
//                        it
//                    } ?: Habit()
//                )
//            }
//        }
//
//        bind.isNewHabit = isNewHabit
//    }

    fun onClickStartColorPicker() {
//        val colorPickerFragment: ColorPickerFragment = habitLocal.value?.color?.let {
//            ColorPickerFragment.newInstance(it)
//        } ?:
//            ColorPickerFragment()
//
//        colorPickerFragment.show(fragmentManager, ColorPickerFragment.TAG)
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