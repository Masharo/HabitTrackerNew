package com.masharo.habits.screens.listHabit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import com.masharo.habits.data.habit.Habit
import com.masharo.habits.adapter.HabitDiffUtilCallback
import com.masharo.habits.adapter.HabitsAdapter
import com.masharo.habits.data.HabitDatabase
import com.masharo.habits.data.habitList.HabitListFilter
import com.masharo.habits.data.habitList.RoomDataHabitList
import com.masharo.habits.databinding.FragmentHabitListBinding

class HabitListViewModel(private val app: Application) : AndroidViewModel(app) {

    private var habitListFilter: HabitListFilter = HabitListFilter()
    private val db = RoomDataHabitList(HabitDatabase.instance(app.applicationContext))
    lateinit var habits: LiveData<List<Habit>>
    private var type: Int? = null
    lateinit var adapter: HabitsAdapter

    fun instance(
        bind: FragmentHabitListBinding,
        owner: LifecycleOwner,
        type: Int?
    ) {
        bind.viewModel = this
        this.type = type

        habits = db.getHabits()

        adapter = bind.recyclerViewHabitsListHabits.adapter as HabitsAdapter

        habitListFilter.getHabits().observe(owner) {
            habitListChange(it)
        }

        habits.observe(owner) {
            habitListFilter.habitsOrigin = it
        }
    }

    private fun habitListChange(list: List<Habit>) {
        val habitDiffUtilCallback = HabitDiffUtilCallback(adapter.habits ?: arrayListOf(), list)
        val resultDiff = DiffUtil.calculateDiff(habitDiffUtilCallback)
        adapter.habits = list
        resultDiff.dispatchUpdatesTo(adapter)
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

    fun removeHabit(id: Int) {
    }

    fun swapHabit(id1: Int, id2: Int) {
    }
}