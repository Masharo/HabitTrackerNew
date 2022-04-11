package com.masharo.habits

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class HabitListViewModel : ViewModel() {

    fun getHabits(): LiveData<List<Habit>> {
        return Data.data()
    }

    fun removeHabit(id: Int) {
        Data.remove(id)
    }

    fun swapHabit(id1: Int, id2: Int) {
        Data.swap(id1, id2)
    }
}