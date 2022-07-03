package com.masharo.habits.presentation.habit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.masharo.habits.R
import com.masharo.habits.databinding.FragmentColorPickerBinding
import javax.inject.Inject

const val ARG_COLOR = "color"
const val COUNT_RECTANGLE: Int = 16

class ColorPickerFragment: DialogFragment(R.layout.fragment_color_picker) {

    private val bind: FragmentColorPickerBinding by viewBinding()
    @Inject
    lateinit var vmFactory: HabitViewModelFactory
    private val vm: HabitViewModel by lazy {
        ViewModelProvider(
            requireParentFragment()
        ).get(HabitViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.vm = vm
        createRectangleForGradient()

        bind.buttonColorPickerOK.setOnClickListener {
            dismiss()
        }
    }

    private fun createRectangleForGradient() {
        if (COUNT_RECTANGLE > 0 ) {

            val changeColorClick = View.OnClickListener {
                vm.colorCalculated(
                    view = it,
                    rootView = bind.viewColorPickerBackgroundGradient
                )
            }

            repeat(COUNT_RECTANGLE) {
                val viewRectangle = layoutInflater.inflate(
                    R.layout.rectangle_color_picker_view,
                    bind.linearLayoutColorPickerGradient,
                    false
                )

                viewRectangle.setOnClickListener(changeColorClick)

                bind.linearLayoutColorPickerGradient.addView(viewRectangle)
            }

        }
    }


    companion object {
        const val TAG = "ColorPicker"
    }
}