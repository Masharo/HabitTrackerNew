package com.masharo.habits.presentation.habit

import android.content.Context
import android.view.View
import androidx.core.view.drawToBitmap
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.*
import com.masharo.habits.data.HabitDatabase
import com.masharo.habits.data.model.Habit
import com.masharo.habits.data.HabitRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitViewModel(
    context: Context,
    private val habitId: Int?
): ViewModel(), Observable {

//    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
//    private lateinit var fragmentManager: FragmentManager

    private val dataLogic = HabitRepositoryImpl(HabitDatabase.instance(context))
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

    fun colorCalculated(view: View, rootView: View) {
//      Нам все равно на высоту потому, что градиент горизонтальный
//      val y = (rootView.y / 2).toInt()

        val y = 0
        val x = view.background.bounds.centerX() + view.x.toInt()

        habit.value?.color = rootView.drawToBitmap().getColor(x, y).toArgb()

//        var arrF = FloatArray(3)
//        Color.colorToHSV(rootView.drawToBitmap().getColor(x, y).toArgb(), arrF)

//        rootView.drawToBitmap().getColor(x, y).getComponents(null).map { it * 255 }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }
}