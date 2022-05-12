package com.masharo.habits.data.remote

import com.masharo.habits.data.remote.model.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.*

interface HabitApi {

    @GET("./habit")
    fun getHabits(): Call<List<HabitRemote>>

    @GET("./habit")
    fun getHabitsTest(): Observable<List<HabitRemote>>

    @PUT("./habit")
    fun setHabit(@Body habit: HabitRemote): Observable<PutResult>

    @PUT("./habit")
    fun addHabit(@Body habit: NewHabitRemote): Observable<PutResult>

    @HTTP(method = "DELETE", path = "./habit", hasBody = true)
    fun deleteHabit(@Body params: DeleteParams): Call<Void>

    @POST("./habit_done")
    fun doneHabit(@Body params: DoneParams): Call<Void>
}