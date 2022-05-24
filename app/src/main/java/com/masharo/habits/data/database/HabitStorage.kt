package com.masharo.habits.data.database

import com.masharo.habits.data.model.local.HabitData
import com.masharo.habits.data.model.local.IdData
import kotlinx.coroutines.flow.Flow

interface HabitStorage {

    suspend fun edit(habit: HabitData)

    suspend fun add(habit: HabitData): Long

    suspend fun delete(id: IdData)

    suspend fun get(id: IdData): HabitData?

    fun getAll(): Flow<List<HabitData>>

}