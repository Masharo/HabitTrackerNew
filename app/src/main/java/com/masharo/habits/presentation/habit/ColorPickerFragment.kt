package com.masharo.habits.presentation.habit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.get
import androidx.core.view.drawToBitmap
import androidx.fragment.app.DialogFragment
import com.masharo.habits.databinding.FragmentColorPickerBinding

const val ARG_COLOR = "color"

class ColorPickerFragment(private val vm: HabitViewModel) : DialogFragment() {

    private lateinit var bind: FragmentColorPickerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentColorPickerBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            setColor(it.getInt(ARG_COLOR))
        }

        bind.selectColor = View.OnClickListener {
            val rootView: View = bind.viewColorPickerBackgroundGradient

//            Нам все равно на высоту потому, что градиент горизонтальный
//            val y = (rootView.y / 2).toInt()

            val y = 0
            val x = it.background.bounds.centerX() + it.x.toInt()

            val color = rootView.drawToBitmap().getColor(x, y)

            val redValue = color.red()
            val blueValue = color.blue()
            val greenValue = color.green()

            setColor(color.toArgb())
        }

        bind.buttonColorPickerOK.setOnClickListener {
            dismiss()
        }
    }

    private fun setColor(color: Int) {
        vm.habit.value?.let {
            it.color = color
            bind.viewColorPickerResult.background = color.toDrawable()
        }
    }

    companion object {
        const val TAG = "ColorPicker"
    }
}