package com.masharo.habits.dataNew.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.masharo.habits.dataNew.model.HabitData
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habit")
    fun getAll(): Flow<List<HabitData>>

    @Query("DELETE FROM habit")
    fun deleteAll()

    @Insert
    fun add(vararg habits: HabitData)

    @Query("DELETE FROM habit WHERE _id_ = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM habit WHERE _id_ = :id")
    suspend fun get(id: Int): HabitData?

    @Update(entity = HabitData::class)
    suspend fun set(habit: HabitData)

}