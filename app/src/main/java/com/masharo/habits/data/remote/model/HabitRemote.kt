package com.masharo.habits.data.remote.model

import com.google.gson.annotations.SerializedName
import com.masharo.habits.data.db.model.Habit

data class HabitRemote(
    @SerializedName("color")
    val questColor: Int,

    @SerializedName("count")
    val questCount: Int,

    @SerializedName("date")
    val questDate: Int,

    @SerializedName("description")
    val questDescription: String,

    @SerializedName("done_dates")
    val questDoneDates: ArrayList<Int>,

    @SerializedName("frequency")
    val questFrequency: Int,

    @SerializedName("priority")
    val questPriority: Int,

    @SerializedName("title")
    val questTitle: String,

    @SerializedName("type")
    val questType: Int,

    @SerializedName("uid")
    val questId: String
) {
    fun convertToHabit(): Habit {
        val habit = Habit()

        habit.title = questTitle
        habit.description = questDescription
        habit.count = questCount
        habit.countDone = questDoneDates.size
        habit.period = questFrequency
        habit.priority = questPriority
        habit.type = questType

        return habit
    }
}