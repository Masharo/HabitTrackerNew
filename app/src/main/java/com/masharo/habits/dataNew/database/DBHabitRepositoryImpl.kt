package com.masharo.habits.dataNew.database

import com.masharo.habits.dataNew.dataToDomainHabit
import com.masharo.habits.dataNew.domainToDataHabit
import com.masharo.habits.domain.DBHabitRepository
import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DBHabitRepositoryImpl(
    private val db: HabitDatabase
): DBHabitRepository {

    override suspend fun editHabit(habit: Habit) {
        db.getHabitDao().set(domainToDataHabit(habit))
    }

    override suspend fun addHabit(habit: Habit): Long {
        return db.getHabitDao().add(domainToDataHabit(habit))
    }

    override suspend fun deleteHabit(id: Id) {
        db.getHabitDao().delete(id.id)
    }

    override suspend fun getHabit(id: Id): Habit? {
        return db.getHabitDao().get(id.id)?.let {
            dataToDomainHabit(it)
        }
    }

    override fun getAllHabits(): Flow<List<Habit>> {
        return db.getHabitDao().getAll()
            .map { habits -> habits.map {
                dataToDomainHabit(it)
            }}
    }

}