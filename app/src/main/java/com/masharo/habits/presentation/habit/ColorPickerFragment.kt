package com.masharo.habits.presentation.habit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.masharo.habits.R
import com.masharo.habits.databinding.FragmentColorPickerBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

const val ARG_COLOR = "color"
const val COUNT_RECTANGLE: Int = 16

class ColorPickerFragment: DialogFragment() {

    private lateinit var bind: FragmentColorPickerBinding
    private val vm: HabitViewModel by lazy {
        requireParentFragment().getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentColorPickerBinding.inflate(inflater, container, false)
        bind.vm = vm
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            for (i in 1..COUNT_RECTANGLE) {
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