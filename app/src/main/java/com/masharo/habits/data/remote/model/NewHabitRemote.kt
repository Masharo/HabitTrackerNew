package com.masharo.habits.data.remote.model

import com.google.gson.annotations.SerializedName
import com.masharo.habits.data.db.model.Habit

data class NewHabitRemote(
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
    val questType: Int
)

fun convertToNewHabitRemote(habit: Habit) =
    NewHabitRemote(
        questTitle = habit.title,
        questDescription = habit.description,
        questCount = habit.count,
        questDoneDates = arrayListOf(),
        questFrequency = habit.period,
        questPriority = habit.priority,
        questType = habit.type,
        questDate = 0,
        questColor = 0
    )