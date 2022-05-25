package com.masharo.habits.domain.model

data class Habit(
    var id: Int? = null,
    val idRemote: String? = null,
    val dateRemote: Int,
    val title: String,
    val description: String,
    val priority: Int,
    val type: Int,
    val count: Int,
    val period: Int,
    var countDone: Int,
    val color: Int
)