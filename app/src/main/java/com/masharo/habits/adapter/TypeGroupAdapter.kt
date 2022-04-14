package com.masharo.habits.adapter

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.masharo.habits.data.habit.Habit
import com.masharo.habits.databinding.FragmentHabitBinding
import com.masharo.habits.screens.listHabit.HabitListFragment

class TypeGroupAdapter: RecyclerView.Adapter<PagerVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        PagerVH()
    }

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int  = Habit.TypeHabit.values().size
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var bind =
}

//class TypeGroupAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
//
//    val fragment = HabitListFragment()
//
//    override fun getItemCount(): Int = Habit.TypeHabit.values().size
//
//    override fun createFragment(position: Int): Fragment {
////        fragment.arguments
//        return fragment
//    }
//}