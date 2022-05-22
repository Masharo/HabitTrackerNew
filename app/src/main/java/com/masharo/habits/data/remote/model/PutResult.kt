package com.masharo.habits.data.remote.model

import com.google.gson.annotations.SerializedName

data class PutResult(
    @SerializedName("uid")
    val id: String
)