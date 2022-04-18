package com.masharo.habits.screens.listHabit

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.masharo.habits.adapter.HabitsAdapter
import com.masharo.habits.data.HabitDatabase
import com.masharo.habits.data.habit.Habit
import com.masharo.habits.data.habitList.HabitListFilter
import com.masharo.habits.data.habitList.RoomDataHabitList

class HabitListViewModel(val context: Context, val adapter: HabitsAdapter): ViewModelProvider.NewInstanceFactory() {

    private var habitListFilter: HabitListFilter = HabitListFilter()
    private val db = RoomDataHabitList(HabitDatabase.instance(context))
    val habits: LiveData<List<Habit>> = db.getHabits()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(
                Context::class.java,
                HabitsAdapter::class.java
            )
            .newInstance(
                context,
                adapter
            )
    }

    fun instance(
    ) {

        habits.observe {
            habitListFilter.habitsOrigin = it
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

    fun getChangeFiltersAndSearch(): LiveData<List<Habit>> {
        return habitListFilter.getHabits()
    }

    fun setNewHabitList(habits: List<Habit>) {

    }

}