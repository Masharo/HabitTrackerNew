package com.masharo.habits.dataNew.model.remote

import com.google.gson.annotations.SerializedName

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

)