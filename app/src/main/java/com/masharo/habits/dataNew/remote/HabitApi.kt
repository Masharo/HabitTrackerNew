package com.masharo.habits.dataNew.remote

import com.masharo.habits.dataNew.model.HabitData
import com.masharo.habits.dataNew.model.IdData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface HabitApi {

    @GET("./habit")
    suspend fun getHabits(): Response<List<HabitData>>

    @PUT("./habit")
    suspend fun putHabit(@Body habit: HabitData): Response<IdData>

    @HTTP(method = "DELETE", path = "./habit", hasBody = true)
    fun deleteHabit(@Body id: IdData): Call<Void>

    @POST("./habit_done")
    fun doneHabit(@Body id: IdData): Call<Void>
}