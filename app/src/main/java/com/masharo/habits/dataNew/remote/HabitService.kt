package com.masharo.habits.dataNew.remote

import com.masharo.habits.dataNew.model.local.IdRemote
import com.masharo.habits.dataNew.model.remote.ParamHabitPutRemote

interface HabitService {

    suspend fun incGoneCountHabitRemote(id: IdRemote)

    suspend fun putHabitRemote(param: ParamHabitPutRemote)

    suspend fun deleteHabitRemote(id: IdRemote)

    suspend fun updateHabits()
}