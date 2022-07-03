package com.masharo.habits.presentation.listHabit

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import by.kirich1409.viewbindingdelegate.viewBinding
import com.masharo.habits.R
import com.masharo.habits.adapter.HabitDiffUtilCallback
import com.masharo.habits.adapter.HabitsAdapter
import com.masharo.habits.app.App
import com.masharo.habits.databinding.FragmentHabitListBinding
import com.masharo.habits.presentation.habit.ARG_ID
import com.masharo.habits.presentation.model.HabitPresentation
import javax.inject.Inject

const val TYPE_HABIT = "typeHabit"

class HabitListFragment : Fragment(R.layout.fragment_habit_list) {

    private val bind: FragmentHabitListBinding by viewBinding()
    private var type: Int = 0
    @Inject lateinit var vmFactory: HabitListViewModelFactory
    private val vm: HabitListViewModel by lazy {
        ViewModelProvider(activity?.viewModelStore ?: throw NullPointerException(
            "Activity is null. HabitListFragment"
        ), vmFactory).get(HabitListViewModel::class.java)
    }
    private lateinit var adapter: HabitsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.apply {
            (applicationContext as App).appComponent.inject(this@HabitListFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        type = arguments?.getInt(TYPE_HABIT, 0) ?: 0

        adapter = HabitsAdapter(
            habits = null,
            onClickItem = {
                view.findNavController().navigate(R.id.habitFragment, bundleOf(Pair(ARG_ID, it)))
            },
            onClickDone = {
                vm.incDoneCount(it)
            }
        )

        bind.adapter = adapter

        vm.getChangeHabits().observe(viewLifecycleOwner) {
            habitListChange( it )
        }

        vm.toast.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), getString(it.first, it.second), Toast.LENGTH_SHORT).show()
        }

        vm.habits.observe(viewLifecycleOwner) {
            vm.setNewHabitList(it)
        }
    }

    private fun habitListChange(list: List<HabitPresentation>) {
        val listFilterType = list.filter { it.type == type }

        DiffUtil.calculateDiff(
                HabitDiffUtilCallback(
                        oldHabits = adapter.habits ?: arrayListOf(),
                        newHabits = listFilterType
                )
        ).dispatchUpdatesTo(adapter)

        adapter.habits = listFilterType
    }

    companion object {

        @JvmStatic
        fun newInstance(typeHabit: Int) =
            HabitListFragment().apply {
                arguments = Bundle().apply {
                    putInt(TYPE_HABIT, typeHabit)
                }
            }
    }
}