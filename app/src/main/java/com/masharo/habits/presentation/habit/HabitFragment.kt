package com.masharo.habits.presentation.habit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.masharo.habits.R
import com.masharo.habits.app.App
import com.masharo.habits.databinding.FragmentHabitBinding
import com.masharo.habits.presentation.listHabit.HabitListViewModel
import com.masharo.habits.presentation.listHabit.HabitListViewModelFactory
import javax.inject.Inject

const val ARG_ID = "ID"

class HabitFragment : Fragment(R.layout.fragment_habit) {

    private val bind: FragmentHabitBinding by viewBinding()
    private val vm: HabitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.apply {
            (applicationContext as App).appComponent.inject(this@HabitFragment)
        }

        arguments?.apply {
            if (containsKey(ARG_ID)) {
                vm.setHabit(getInt(ARG_ID))
            }
        }

        childFragmentManager.setFragmentResultListener(ColorPickerFragment.TAG, this) { _, bundle ->
            vm.changeHabitColor(bundle.getInt(ARG_COLOR))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.vm = vm
        bind.lifecycleOwner = viewLifecycleOwner

        context?.let {
            bind.spinnerHabitPriority.adapter = ArrayAdapter(
                it,
                R.layout.habit_spinner_item,
                resources.getStringArray(R.array.habit_priority)
            )

            bind.spinnerHabitPeriod.adapter = ArrayAdapter(
                it,
                R.layout.habit_spinner_item,
                resources.getStringArray(R.array.habit_period)
            )

            bind.buttonHabitSave.setOnClickListener {
                if (bind.editTextHabitTitle.text.toString().isNotBlank() &&
                    bind.editTextHabitDescription.text.toString().isNotBlank()
                ) {
                    vm.save()
                    view.findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), R.string.warn_title_or_description_blank, Toast.LENGTH_LONG).show()
                }
            }
        }

        bind.buttonHabitColorPicker.setOnClickListener {
            parentFragmentManager.apply {
                if (isAdded) {
                    ColorPickerFragment().show(childFragmentManager, ColorPickerFragment.TAG)
                }
            }
        }
    }
}