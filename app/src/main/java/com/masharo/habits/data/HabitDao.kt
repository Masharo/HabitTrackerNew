package com.masharo.habits.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.masharo.habits.Habit

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit ORDER BY id DESC")
    fun habits(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit WHERE type = :type ORDER BY id DESC")
    fun habitsTypeFilter(type: Int): LiveData<List<Habit>>

    @Insert
    fun add(habit: Habit)

    @Query("SELECT * FROM habit WHERE id = :id")
    fun get(id: Int): Habit?

    @Update(entity = Habit::class)
    fun set(habit: Habit)
}