package com.masharo.habits.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.masharo.habits.data.db.model.Habit
import com.masharo.habits.presentation.listHabit.HabitListFragment

class TypeGroupAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = Habit.TypeHabit.values().size

    override fun createFragment(position: Int): Fragment = HabitListFragment.newInstance(position)

}