package com.masharo.habits.adapter

import androidx.recyclerview.widget.DiffUtil
import com.masharo.habits.presentation.model.HabitPresentation

class HabitDiffUtilCallback(
    private val oldHabits: List<HabitPresentation>,
    private val newHabits: List<HabitPresentation>
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