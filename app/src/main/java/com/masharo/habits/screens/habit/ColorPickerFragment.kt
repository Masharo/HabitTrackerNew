package com.masharo.habits.screens.habit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.masharo.habits.databinding.FragmentColorPickerBinding

const val ARG_COLOR = "color"

class ColorPickerFragment : DialogFragment() {

    private var color: Int? = null
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
            val y = (rootView.y / 2).toInt()
            val x = view.background.bounds.centerX() + view.x.toInt()

            rootView.isDrawingCacheEnabled = true
            val col = rootView.drawingCache.getColor(x, y)

            rootView.isDrawingCacheEnabled = false

            val redValue = col.red()
            val blueValue = col.blue()
            val greenValue = col.green()

            setColor(col.toArgb())
        }

        bind.buttonColorPickerOK.setOnClickListener {
            color?.let {
                parentFragmentManager.setFragmentResult(TAG, bundleOf(ARG_COLOR to color))
            }
            dismiss()
        }
    }

    private fun setColor(color: Int) {
        this.color = color
        bind.viewColorPickerResult.background = color.toDrawable()
    }

    companion object {

        const val TAG = "ColorPicker"

        @JvmStatic
        fun newInstance(color: Int) =
            ColorPickerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLOR, color)
                }
            }
    }
}