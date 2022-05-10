package com.masharo.habits.data.remote.model

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String
)