package com.masharo.habits.support

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.databinding.InverseMethod
import androidx.databinding.adapters.Converters
import com.masharo.habits.R
import com.masharo.habits.presentation.model.HabitPresentation

object SupportDataBinding {

    fun convertStringToInt(text: String) =
        when (text) {
            "" -> 0
            else -> text.toInt()
        }


    @InverseMethod(value="convertStringToInt")
    fun convertIntToString(value: Int) =
         when (value) {
             0 -> ""
             else -> value.toString()
         }

    fun convertColorToXML(valueColor: Int): ColorDrawable =
        Converters.convertColorToDrawable(valueColor)

    @InverseMethod(value="convertColorToXML")
    fun convertXMLToColor(valueColor: ColorDrawable) = valueColor.color

    fun getPriority(habit: HabitPresentation, context: Context): String {
        val priority = context.resources.getStringArray(R.array.habit_priority)[habit.priority]

        return context.getString(
            R.string.textView_habitItem_priority,
            priority.lowercase()
        )
    }

    fun getPeriod(habit: HabitPresentation, context: Context): String {
        val period = context.resources.getStringArray(R.array.habit_period)[habit.period]

        return context.getString(
            R.string.textView_habitItem_period,
            period,
            habit.countDone,
            habit.count
        )
    }

    fun converterTypeHabit(habit: HabitPresentation) = habit.getTypeEnum().resourceString()

    @InverseMethod(value = "converterTypeHabit")
    fun converterTypeHabit(id: Int) = HabitPresentation.TypeHabit.searchForResource(id)

    fun getTypeImage(context: Context, image: Int): Drawable? {
        return if (image == 0) {
            ContextCompat.getDrawable(context, R.drawable.ic_smailhappy)
        } else {
            ContextCompat.getDrawable(context, image)
        }
    }

    fun convertColorToRgb(context: Context, valueColor: Int?): String =
        valueColor?.let {
            val colors = Color
                .valueOf(valueColor)
                .getComponents(null)
                .map { (it * 255).toInt() }

            context.getString(
                R.string.rgb,
                colors[0],
                colors[1],
                colors[2]
            )

        } ?: run {
            convertColorToRgb(context, Color.WHITE)
        }

    fun convertColorToHsv(context: Context, valueColor: Int?): String =
        valueColor?.let {
            val colors = FloatArray(3)
            Color.colorToHSV(Color.valueOf(valueColor).toArgb(), colors)

            context.getString(
                R.string.hsv,
                colors[0].toInt(),
                (colors[1] * 100).toInt(),
                (colors[2] * 100).toInt()
            )

        } ?: run {
            convertColorToHsv(context, Color.WHITE)
        }

}