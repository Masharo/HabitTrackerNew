package com.masharo.habits.presentation.listHabit

import android.app.Application
import androidx.lifecycle.*
import com.masharo.habits.data.HabitDatabase
import com.masharo.habits.data.habit.Habit
import com.masharo.habits.data.habitList.HabitListFilter
import com.masharo.habits.data.habitList.RoomDataHabitList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitListViewModel(app: Application): AndroidViewModel(app) {

    private var habitListFilter: HabitListFilter = HabitListFilter()
    private val db = RoomDataHabitList(HabitDatabase.instance(app.applicationContext))
    var habits: LiveData<List<Habit>> = MutableLiveData()
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            habits = db.getHabits()
        }
    }

    fun setFilterType(type: Int) {
        habitListFilter.setFilter(HabitListFilter.Column.TYPE, type)
    }

    fun setSort(column: HabitListFilter.Column) {
        habitListFilter.sortColumn = column
    }

    fun setSearch(search: String) {
        habitListFilter.search = search
    }

    fun getChangeHabits(): LiveData<List<Habit>> {
        return habitListFilter.getHabits()
    }

    fun setNewHabitList(habits: List<Habit>) {
        habitListFilter.habitsOrigin = habits
    }
}