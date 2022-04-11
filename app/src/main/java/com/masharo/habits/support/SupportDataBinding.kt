package com.masharo.habits.support

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.databinding.InverseMethod
import androidx.databinding.adapters.Converters
import com.masharo.habits.data.habit.Habit
import com.masharo.habits.R

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

    fun convertColorToXML(valueColor: Int?): ColorDrawable =
        if (valueColor != null) {
            Converters.convertColorToDrawable(valueColor)
        } else {
            Converters.convertColorToDrawable(Color.WHITE)
        }

    @InverseMethod(value="convertColorToXML")
    fun convertXMLToColor(valueColor: String) = Color.parseColor(valueColor)

    fun getPriority(habit: Habit, context: Context): String {
        val priority = context.resources.getStringArray(R.array.habit_priority)[habit.priority]

        return context.getString(
            R.string.textView_habitItem_priority,
            priority.lowercase()
        )
    }

    fun getPeriod(habit: Habit, context: Context): String {
        val period = context.resources.getStringArray(R.array.habit_period)[habit.period]

        return context.getString(
            R.string.textView_habitItem_period,
            period,
            habit.countDone,
            habit.count
        )
    }

    fun converterTypeHabit(habit: Habit) = habit.getTypeEnum().resourceString()

    @InverseMethod(value = "converterTypeHabit")
    fun converterTypeHabit(id: Int) = Habit.TypeHabit.searchForResource(id)
}