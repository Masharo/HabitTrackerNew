package com.masharo.habits.data.remote

import com.masharo.habits.data.model.local.IdData
import com.masharo.habits.data.model.remote.ParamHabitPutRemote

interface HabitService {

    suspend fun incGoneCountHabitRemote(param: IdData)

    suspend fun putHabitRemote(param: ParamHabitPutRemote)

    suspend fun deleteHabitRemote(param: IdData)

    suspend fun updateHabits()

}