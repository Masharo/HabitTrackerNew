package com.masharo.habits.screens.listHabit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import com.masharo.habits.Habit
import com.masharo.habits.adapter.HabitsAdapter
import com.masharo.habits.data.HabitDatabase
import com.masharo.habits.databinding.FragmentHabitListBinding
import com.masharo.habits.adapter.HabitDiffUtilCallback
import com.masharo.habits.data.habitList.RoomDataHabitList

class HabitListViewModel(private val app: Application) : AndroidViewModel(app) {

    private val db = RoomDataHabitList(HabitDatabase.instance(app.applicationContext))
    private lateinit var habits: LiveData<List<Habit>>
    private lateinit var habitOpenLambda: (Int?) -> Unit
    private var type: Int? = null
    lateinit var adapter: HabitsAdapter

    fun instance(
        bind: FragmentHabitListBinding,
        owner: LifecycleOwner,
        type: Int?,
        addLambda: (Int?) -> Unit
    ) {
        this.habitOpenLambda = addLambda
        bind.viewModel = this
        this.type = type

        habits = type?.let {
            db.getHabitsTypeFilter(Habit.TypeHabit.values()[it])
            } ?: db.getHabits()

        adapter = HabitsAdapter(app.applicationContext, habits.value, addLambda)

        habits.observe(owner) {
            val habitDiffUtilCallback = HabitDiffUtilCallback(adapter.habits ?: arrayListOf(), it)
            val resultDiff = DiffUtil.calculateDiff(habitDiffUtilCallback)
            adapter.habits = it
            resultDiff.dispatchUpdatesTo(adapter)
        }

    }

    fun onClickAdd() {
        habitOpenLambda(null)
    }

    fun removeHabit(id: Int) {
    }

    fun swapHabit(id1: Int, id2: Int) {
    }
}