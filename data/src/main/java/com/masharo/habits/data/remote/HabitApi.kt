package com.masharo.habits.data.remote

import com.masharo.habits.data.model.remote.DoneRemote
import com.masharo.habits.data.model.remote.HabitRemote
import com.masharo.habits.data.model.remote.IdRemote
import retrofit2.Response
import retrofit2.http.*

interface HabitApi {

    @GET("./habit")
    suspend fun getHabits(): Response<List<HabitRemote>>

    @PUT("./habit")
    suspend fun putHabit(@Body habit: HabitRemote): Response<IdRemote>

    @HTTP(method = "DELETE", path = "./habit", hasBody = true)
    suspend fun deleteHabit(@Body id: IdRemote): Response<Void>

    @POST("./habit_done")
    suspend fun doneHabit(@Body done: DoneRemote): Response<Void>

}