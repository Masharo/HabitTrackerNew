package com.masharo.habits.dataNew.remote

import com.masharo.habits.dataNew.model.local.IdRemote
import com.masharo.habits.dataNew.model.remote.HabitRemote
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface HabitApi {

    @GET("./habit")
    suspend fun getHabits(): Response<List<HabitRemote>>

    @PUT("./habit")
    suspend fun putHabit(@Body habit: HabitRemote): Response<IdRemote>

    @HTTP(method = "DELETE", path = "./habit", hasBody = true)
    fun deleteHabit(@Body id: IdRemote): Call<Void>

    @POST("./habit_done")
    fun doneHabit(@Body id: IdRemote): Call<Void>
}