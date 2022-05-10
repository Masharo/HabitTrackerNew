package com.masharo.habits.data

import androidx.lifecycle.LiveData
import com.masharo.habits.data.db.model.Habit
import com.masharo.habits.data.remote.model.DoneParams
import com.masharo.habits.data.remote.model.HabitRemote
import retrofit2.Call

interface HabitRepository {

    fun getHabits(): LiveData<List<Habit>>

    fun getRemoteHabits(): Call<List<HabitRemote>>

    suspend fun addAll(habits: List<Habit>)

//    suspend fun updateHabits(): Boolean

    suspend fun clearHabits(): Boolean

    suspend fun setHabit(habit: Habit)

    suspend fun getHabit(id: Int): Habit?

    suspend fun addHabit(habit: Habit)

    fun doneHabit(params: DoneParams): Call<Void>
}