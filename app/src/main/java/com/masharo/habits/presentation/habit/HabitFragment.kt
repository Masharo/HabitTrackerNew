package com.masharo.habits.presentation.habit

import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.masharo.habits.R
import com.masharo.habits.databinding.FragmentHabitBinding
import org.koin.androidx.viewmodel.ViewModelParameter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

const val ARG_ID = "ID"

class HabitFragment : Fragment() {

    private lateinit var bind: FragmentHabitBinding
    private var habitId: Int? = null
    private val vm: HabitViewModel by viewModel { parametersOf(habitId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            habitId = it.getInt(ARG_ID)
        }

        childFragmentManager.setFragmentResultListener(ColorPickerFragment.TAG, this) { _, bundle ->
            vm.changeHabitColor(bundle.getInt(ARG_COLOR))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentHabitBinding.inflate(inflater, container, false)

        bind.vm = vm
        bind.lifecycleOwner = viewLifecycleOwner

        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                if (bind.editTextHabitTitle.text.toString().isNotBlank()) {
                    vm.save()
                    view.findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(), R.string.warn_title_blank, Toast.LENGTH_LONG).show()
                }
            }
        }

        bind.buttonHabitColorPicker.setOnClickListener {
            parentFragmentManager.apply {
                if (isAdded) {
                    ColorPickerFragment(vm).show(this, ColorPickerFragment.TAG)
                }
            }
        }
    }
}