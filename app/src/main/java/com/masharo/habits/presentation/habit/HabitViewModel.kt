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
import com.masharo.habits.domain.model.Id
import com.masharo.habits.domain.usecase.AddHabitUseCase
import com.masharo.habits.domain.usecase.EditHabitUseCase
import com.masharo.habits.domain.usecase.GetHabitUseCase
import com.masharo.habits.presentation.domainToPresentationHabit
import com.masharo.habits.presentation.model.HabitPresentation
import com.masharo.habits.presentation.presentationToDomainHabit
import kotlinx.coroutines.launch
import java.util.*

class HabitViewModel(
    private val context: Context,
    private val habitId: Int?,
    private val getHabitUseCase: GetHabitUseCase,
    private val addHabitUseCase: AddHabitUseCase,
    private val editHabitUseCase: EditHabitUseCase
): ViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()
    private val habitLocal: MutableLiveData<HabitPresentation> = MutableLiveData(HabitPresentation())

    @Bindable
    val habit: LiveData<HabitPresentation> = habitLocal

    init {
        habitId?.let {
            viewModelScope.launch {
                getHabitUseCase.execute(Id(it))?.let { habit->
                    habitLocal.postValue(domainToPresentationHabit(habit))
                }
            }
        }
    }

    fun save() {

        habitLocal.value?.let { habit ->
            habit.dateRemote = (Calendar.getInstance().timeInMillis * 0.001).toInt()

            habit.id?.let {
                viewModelScope.launch {
                    editHabitUseCase.execute(presentationToDomainHabit(habit))
                }
            } ?: run {
                viewModelScope.launch {
                    addHabitUseCase.execute(presentationToDomainHabit(habit))
                }
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