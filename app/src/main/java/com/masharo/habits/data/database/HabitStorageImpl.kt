package com.masharo.habits.data.database

import com.masharo.habits.data.model.local.HabitData
import com.masharo.habits.data.model.local.IdData
import kotlinx.coroutines.flow.Flow

class HabitStorageImpl(
    private val db: HabitDatabase
): HabitStorage {

    override suspend fun edit(habit: HabitData) {
        db.getHabitDao().set(habit)
    }

    override suspend fun add(habit: HabitData): Long {
        return db.getHabitDao().add(habit)
    }

    override suspend fun delete(id: IdData) {
        db.getHabitDao().delete(id.id)
    }

    override suspend fun get(id: IdData): HabitData? {
        return db.getHabitDao().get(id.id)
    }

    override fun getAll(): Flow<List<HabitData>> {
        return db.getHabitDao().getAll()
    }

}