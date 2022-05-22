package com.masharo.habits.data.remote.model

import com.google.gson.annotations.SerializedName

data class DoneParams(
    @SerializedName("date")
    val date: Int,

    @SerializedName("habit_uid")
    val id: String
)