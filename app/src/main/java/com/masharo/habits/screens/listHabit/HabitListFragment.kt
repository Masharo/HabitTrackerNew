package com.masharo.habits.screens.listHabit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.masharo.habits.R
import com.masharo.habits.adapter.HabitDiffUtilCallback
import com.masharo.habits.adapter.HabitsAdapter
import com.masharo.habits.data.habit.Habit
import com.masharo.habits.databinding.FragmentHabitListBinding
import com.masharo.habits.screens.habit.ARG_ID
import com.masharo.habits.support.InitViewModel

const val TYPE_HABIT = "typeHabit"

class HabitListFragment : Fragment() {

    private lateinit var bind: FragmentHabitListBinding
    private var type: Int = 0
    private lateinit var vm: HabitListViewModel
    private lateinit var adapter: HabitsAdapter

    companion object {

        @JvmStatic
        fun newInstance(typeHabit: Int) =
            HabitListFragment().apply {
                arguments = Bundle().apply {
                    putInt(TYPE_HABIT, typeHabit)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentHabitListBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onResume() {
        super.onResume()
        vm.setFilterType(type)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        type = arguments?.getInt(TYPE_HABIT, 0) ?: 0

        adapter = HabitsAdapter(null) {
            view.findNavController().navigate(R.id.habitFragment, bundleOf(Pair(ARG_ID, it)))
        }

        bind.adapter = adapter

        if (SortAndSearchFragment.size() == 0) {
            SortAndSearchFragment.add(activity as ViewModelStoreOwner)
        }

        vm = HabitListViewModel.get(activity as ViewModelStoreOwner)

        vm.getChangeHabits().observe(viewLifecycleOwner) {
            habitListChange(it)
        }

        vm.habits.observe(viewLifecycleOwner) {
            vm.setNewHabitList(it)
        }
    }

    private fun habitListChange(list: List<Habit>) {
        val listFilterType = list.filter { it.type == type }
        val hduc = HabitDiffUtilCallback(adapter.habits ?: arrayListOf(), listFilterType)
        val resultDiff = DiffUtil.calculateDiff(hduc)
        adapter.habits = listFilterType
        resultDiff.dispatchUpdatesTo(adapter)
    }
}