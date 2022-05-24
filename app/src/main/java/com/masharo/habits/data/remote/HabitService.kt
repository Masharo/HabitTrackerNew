package com.masharo.habits.data.remote

import com.masharo.habits.data.model.local.IdData
import com.masharo.habits.data.model.remote.ParamHabitPutRemote

interface HabitService {

    suspend fun incGoneCountHabitRemote(id: IdData)

    suspend fun putHabitRemote(param: ParamHabitPutRemote)

    suspend fun deleteHabitRemote(id: IdData)

    suspend fun updateHabits()

}