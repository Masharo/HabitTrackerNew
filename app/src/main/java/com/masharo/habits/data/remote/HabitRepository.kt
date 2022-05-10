package com.masharo.habits.data.remote

import com.masharo.habits.data.remote.model.*
import retrofit2.Call

interface HabitRepository {
    fun getHabits(): Call<List<HabitRemote>>

    fun putHabit(habit: HabitRemote): Call<PutResult>

    fun addHabit(habit: NewHabitRemote): Call<PutResult>

    fun deleteHabit(params: DeleteParams): Call<Void>

    fun doneHabit(params: DoneParams): Result<Void>
}