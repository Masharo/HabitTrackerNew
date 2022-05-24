package com.masharo.habits.dataNew.remote

import com.masharo.habits.dataNew.model.local.IdData
import com.masharo.habits.dataNew.model.remote.ParamHabitPutRemote

interface HabitService {

    suspend fun incGoneCountHabitRemote(id: IdData)

    suspend fun putHabitRemote(param: ParamHabitPutRemote)

    suspend fun deleteHabitRemote(id: IdData)

    suspend fun updateHabits()

}