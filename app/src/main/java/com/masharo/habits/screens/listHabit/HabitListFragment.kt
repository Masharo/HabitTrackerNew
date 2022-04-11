package com.masharo.habits.screens.listHabit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import com.masharo.habits.R
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

        val viewModel = InitViewModel.instanceHabitListViewModel(
            this as ViewModelStoreOwner,
            bind,
            this as LifecycleOwner,
            type
        ) {
            val bundleFragment = bundleOf()

            it?.let {
                bundleFragment.putInt(ARG_ID, it)
            }

            view.findNavController().navigate(R.id.habitFragment, bundleFragment)
        }
    }
}