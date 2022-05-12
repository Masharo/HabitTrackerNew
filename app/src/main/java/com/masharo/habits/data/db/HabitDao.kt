package com.masharo.habits.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.masharo.habits.data.db.model.Habit

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit")
    fun getAll(): LiveData<List<Habit>>

    @Query("DELETE FROM habit")
    suspend fun deleteAll()

    @Insert
    suspend fun add(habit: Habit): Long

    @Insert
    suspend fun addAll(habits: List<Habit>)

    @Query("SELECT * FROM habit WHERE ${Habit.DB_ID} = :id")
    suspend fun get(id: Int): Habit?

    @Update(entity = Habit::class)
    suspend fun set(habit: Habit)
}