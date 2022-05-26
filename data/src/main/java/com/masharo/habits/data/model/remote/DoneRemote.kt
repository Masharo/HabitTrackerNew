package com.masharo.habits.data.model.remote

import com.google.gson.annotations.SerializedName

data class DoneRemote(

    @SerializedName("habit_uid")
    val id: String,

    @SerializedName("date")
    val date: Int

)