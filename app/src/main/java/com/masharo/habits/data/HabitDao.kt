package com.masharo.habits.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.masharo.habits.data.habit.Habit

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit ORDER BY ${Habit.DB_ID} DESC")
    fun habits(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE ${Habit.DB_TYPE} = :type")
    fun habitsTypeFilter(type: Int): LiveData<List<Habit>>

    @Insert
    fun add(habit: Habit)

    @Query("SELECT * FROM habit WHERE ${Habit.DB_ID} = :id")
    fun get(id: Int): Habit?

    @Update(entity = Habit::class)
    fun set(habit: Habit)
}