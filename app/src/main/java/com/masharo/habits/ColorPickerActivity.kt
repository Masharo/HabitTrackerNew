package com.masharo.habits

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import com.masharo.habits.databinding.ActivityColorPickerBinding


class ColorPickerActivity : AppCompatActivity() {

    private lateinit var bind: ActivityColorPickerBinding
    private var color: Int? = null

    companion object {
        const val ID_ACTIVITY = 1
        const val COLOR = "COLOR"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityColorPickerBinding.inflate(layoutInflater)
        setContentView(bind.root)

        setColor(intent.getIntExtra(COLOR, -1026))
        bind.buttonColorPickerOK.setOnClickListener {
            color?.let {
                val returnIntent = Intent()
                returnIntent.putExtra(COLOR, color)
                setResult(RESULT_OK, returnIntent)
            }

            finish()
        }
    }

    fun onClick(view: View) {
        val rootView: View = window.decorView.rootView
        val y = rootView.height - view.background.bounds.centerY() - bind.buttonColorPickerOK.height
        val x = view.background.bounds.centerX() + view.x.toInt() - getScrollX()

        rootView.isDrawingCacheEnabled = true
        val col = rootView.drawingCache.getColor(x, y)

        rootView.isDrawingCacheEnabled = false

        val redValue = col.red()
        val blueValue = col.blue()
        val greenValue = col.green()

        setColor(col.toArgb())
    }

    private fun setColor(color: Int) {
        this.color = color
        bind.viewColorPickerResult.background = color.toDrawable()
    }

    private fun getScrollX() = bind.scrollViewColorPickerColors.scrollX
}