package com.masharo.habits.screens.listHabit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.ViewModelInitializer
import androidx.navigation.findNavController
import com.masharo.habits.R
import com.masharo.habits.adapter.HabitsAdapter
import com.masharo.habits.databinding.FragmentHabitListBinding
import com.masharo.habits.screens.habit.ARG_ID
import com.masharo.habits.support.InitViewModel

const val TYPE_HABIT = "typeHabit"

class HabitListFragment : Fragment() {

    private lateinit var bind: FragmentHabitListBinding
    private var type: Int? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        type = arguments?.getInt(TYPE_HABIT, -1)

        if (type == -1) {
            type = null
        }

        bind.recyclerViewHabitsListHabits.adapter = HabitsAdapter(requireContext(), null) {
            view.findNavController().navigate(R.id.habitFragment, bundleOf(Pair(ARG_ID, it)))
        }

        if (SortAndSearchFragment.size() == 0) {
            SortAndSearchFragment.add(activity as ViewModelStoreOwner)

            InitViewModel.instanceHabitListViewModel(
                activity as ViewModelStoreOwner,
                bind,
                this as LifecycleOwner
            ).setFilterType(type!!)
        } else {
            ViewModelProvider(activity as ViewModelStoreOwner)[HabitListViewModel::class.java].setFilterType(type!!)
        }

        ViewModelProvider(this, object : ViewModelProvider.Factory {

        })
    }
}