package com.masharo.habits.data.repository

import com.masharo.habits.data.dataNew.dataToDomainHabit
import com.masharo.habits.data.dataNew.database.HabitStorage
import com.masharo.habits.data.dataNew.domainToDataHabit
import com.masharo.habits.data.dataNew.domainToDataId
import com.masharo.habits.domain.repository.DBHabitRepository
import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DBHabitRepositoryImpl(
    private val storage: HabitStorage
): DBHabitRepository {

    override suspend fun editHabit(habit: Habit) {
        storage.edit(domainToDataHabit(habit))
    }

    override suspend fun addHabit(habit: Habit): Long {
        return storage.add(domainToDataHabit(habit))
    }

    override suspend fun deleteHabit(id: Id) {
        storage.delete(domainToDataId(id))
    }

    override suspend fun getHabit(id: Id): Habit? {
        return storage.get(domainToDataId(id))?.let {
            dataToDomainHabit(it)
        }
    }

    override fun getAllHabits(): Flow<List<Habit>> {
        return storage.getAll().map { habits ->
            habits.map {
                dataToDomainHabit(it)
            }
        }
    }

}