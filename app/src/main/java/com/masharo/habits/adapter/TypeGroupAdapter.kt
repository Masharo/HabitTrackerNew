package com.masharo.habits.adapter

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.masharo.habits.data.habit.Habit
import com.masharo.habits.screens.listHabit.HabitListFragment
import com.masharo.habits.screens.listHabit.TYPE_HABIT

class TypeGroupAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = Habit.TypeHabit.values().size

    override fun createFragment(position: Int): Fragment = HabitListFragment.newInstance(position)

}