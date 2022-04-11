package com.masharo.habits

import androidx.annotation.IdRes

data class Habit(
    var title: String,
    var description: String,
    var priority: Int,
    var type: Int,
    var count: Int,
    var period: Int,
    var countDone: Int,
    var color: Int?
) {
    companion object {
        fun getIdImage(@IdRes id: Int) =  when(id) {
            R.id.radioButton_habit_positive -> {
                R.drawable.ic_smailhappy
            }
            R.id.radioButton_habit_negative -> {
                R.drawable.ic_smailsad
            }
            else -> {
                R.drawable.ic_smailok
            }
        }
    }
}