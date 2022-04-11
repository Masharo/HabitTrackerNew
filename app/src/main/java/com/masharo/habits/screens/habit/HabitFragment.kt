package com.masharo.habits.screens.habit

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import com.masharo.habits.R
import com.masharo.habits.databinding.FragmentHabitBinding
import com.masharo.habits.support.InitViewModel

const val ARG_ID = "ID"

class HabitFragment : Fragment() {

    private lateinit var bind: FragmentHabitBinding
    private lateinit var viewModel: HabitViewModel
    private var id: Int? = null

    companion object {

        @JvmStatic
        fun newInstance(id: Int) =
            HabitFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, id)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_ID)
        }

        childFragmentManager.setFragmentResultListener(ColorPickerFragment.TAG, this) { key, bundle ->
            viewModel.changeHabitColor(bundle.getInt(ARG_COLOR))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentHabitBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    val color = it.getIntExtra(ARG_COLOR, 0)
                    viewModel.changeHabitColor(color)
                }
            }
        }

        viewModel = InitViewModel.instanceHabitViewModel(
            this as ViewModelStoreOwner,
            bind,
            id,
            resultLauncher,
            childFragmentManager
        )

        bindView()
    }

    private fun bindView() {

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
                    viewModel.onClickButtonSave()
                    view?.findNavController()?.popBackStack()
                } else {
                    Toast.makeText(requireContext(), R.string.warn_title_blank, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}