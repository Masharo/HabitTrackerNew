package com.masharo.habits.presentation.habit

import android.content.Context
import android.view.View
import androidx.core.view.drawToBitmap
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.masharo.habits.HABIT_ID
import com.masharo.habits.data.HabitRepository
import com.masharo.habits.data.db.model.Habit
import com.masharo.habits.data.remote.worker.AddHabitWorker
import com.masharo.habits.data.remote.worker.SetHabitWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class HabitViewModel(
    private val context: Context,
    private val repository: HabitRepository,
    private val habitId: Int?
): ViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()
    private val habitLocal: MutableLiveData<Habit> = MutableLiveData(Habit())

    @Bindable
    val habit: LiveData<Habit> = habitLocal

    init {
        viewModelScope.launch(Dispatchers.IO) {
            habitId?.let { id ->
                repository.getHabit(id)?.let {
                    habitLocal.postValue(it)
                }
            }
        }
    }

    fun save() {

        habitLocal.value?.let { habit ->

            habit.dateRemote = (Calendar.getInstance().timeInMillis * 0.001).toInt()

            habit.id?.let {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.setHabit(habit)
                    workOperation<SetHabitWorker>(it)
                }
            } ?: run {
                viewModelScope.launch(Dispatchers.IO) {
                    workOperation<AddHabitWorker>(
                        repository.addHabit(habit).toInt()
                    )
                }
            }
        }

    }

    private inline fun <reified W : ListenableWorker>workOperation(id: Int) = WorkManager
        .getInstance(context)
        .enqueue(
            OneTimeWorkRequestBuilder<W>()
                .setConstraints(
                    Constraints
                        .Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build())
                .setInputData(
                    Data
                        .Builder()
                        .putInt(HABIT_ID, id)
                        .build()
                )
                .build()
        )

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
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }
}