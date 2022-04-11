package com.masharo.habits.screens.listHabit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.masharo.habits.Habit
import com.masharo.habits.adapter.TypeGroupAdapter
import com.masharo.habits.databinding.FragmentViewPagerHabitListBinding

class ViewPagerHabitListFragment : Fragment() {

    private lateinit var bind: FragmentViewPagerHabitListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentViewPagerHabitListBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TypeGroupAdapter(this)
        bind.viewPagerPagerHabitType.adapter = adapter

        TabLayoutMediator(bind.tabLayoutPagerHabitType, bind.viewPagerPagerHabitType) { tab, position ->
            tab.text = getString(Habit.TypeHabit.values()[position].resourceString())
        }.attach()
    }
}