package com.masharo.habits.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.masharo.habits.data.habit.Habit

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit")
    fun habits(): LiveData<List<Habit>>

    @Insert
    suspend fun add(habit: Habit)

    @Query("SELECT * FROM habit WHERE ${Habit.DB_ID} = :id")
    suspend fun get(id: Int): Habit?

    @Update(entity = Habit::class)
    suspend fun set(habit: Habit)
}