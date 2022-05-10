package com.masharo.habits.data.remote

import com.masharo.habits.data.remote.model.*
import retrofit2.Call
import retrofit2.http.*

interface HabitApi {

    @GET("./habit")
    fun getHabits(): Call<List<HabitRemote>>

    @PUT("./habit")
    fun putHabit(@Body habit: HabitRemote): Call<PutResult>

    @PUT("./habit")
    fun addHabit(@Body habit: NewHabitRemote): Call<PutResult>

    @HTTP(method = "DELETE", path = "./habit", hasBody = true)
    fun deleteHabit(@Body params: DeleteParams): Call<Void>

    @POST("./habit_done")
    fun doneHabit(@Body params: DoneParams): Call<Void>
}