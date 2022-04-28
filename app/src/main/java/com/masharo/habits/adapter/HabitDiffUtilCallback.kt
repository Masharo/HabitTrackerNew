package com.masharo.habits.adapter

import androidx.recyclerview.widget.DiffUtil
import com.masharo.habits.data.habit.Habit

class HabitDiffUtilCallback(
    private val oldHabits: List<Habit>,
    private val newHabits: List<Habit>
    ): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldHabits.size

    override fun getNewListSize(): Int = newHabits.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldHabit = oldHabits[oldItemPosition]
        val newHabit = newHabits[newItemPosition]
        return oldHabit.id == newHabit.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldHabit = oldHabits[oldItemPosition]
        val newHabit = newHabits[newItemPosition]
        return oldHabit == newHabit
    }
}