package com.masharo.habits.data

import androidx.lifecycle.LiveData
import com.masharo.habits.data.db.model.Habit
import com.masharo.habits.data.remote.model.DoneParams
import com.masharo.habits.data.remote.model.HabitRemote
import com.masharo.habits.data.remote.model.PutResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Response

interface HabitRepository {

    fun getHabits(): LiveData<List<Habit>>

    suspend fun getRemoteHabits(): Response<List<HabitRemote>>

    fun addAll(habits: List<Habit>)

    fun clearHabits(): Boolean

    suspend fun setHabit(habit: Habit)

    suspend fun setHabitRemote(habit: Habit): Response<PutResult>

    suspend fun getHabit(id: Int): Habit?

    suspend fun addHabit(habit: Habit): Long

    suspend fun addHabitRemote(habit: Habit): Response<PutResult>

    fun doneHabit(params: DoneParams): Call<Void>
}