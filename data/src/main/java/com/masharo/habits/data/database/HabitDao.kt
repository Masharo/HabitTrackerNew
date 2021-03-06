package com.masharo.habits.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.masharo.habits.data.model.local.HabitData
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits")
    fun getAll(): Flow<List<HabitData>>

    @Query("DELETE FROM habits")
    fun deleteAll()

    @Insert
    fun add(habit: HabitData): Long

    @Insert
    fun addAll(vararg habits: HabitData)

    @Query("DELETE FROM habits WHERE _id_ = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM habits WHERE _id_ = :id")
    suspend fun get(id: Int): HabitData?

    @Update(entity = HabitData::class)
    suspend fun set(habit: HabitData)

}