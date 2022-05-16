package com.masharo.habits.domain.model

data class Habit(
    val id: Int? = null,
    val idRemote: String,
    val dateRemote: Int,
    val title: String,
    val description: String,
    val priority: Int,
    val type: Int,
    val count: Int,
    val period: Int,
    val countDone: Int,
    val color: Int
)