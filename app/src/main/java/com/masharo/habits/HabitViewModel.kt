package com.masharo.habits

import androidx.lifecycle.ViewModel

class HabitViewModel: ViewModel() {

    fun addHabit(habit: Habit) {
        Data.add(habit)
    }

    fun getHabit(id: Int) = Data.get(id)

    fun setHabit(habit: Habit, id: Int) = Data.set(habit, id)
}