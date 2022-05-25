package com.masharo.habits.domain.usecase

import com.masharo.habits.domain.repository.DBHabitRepository
import com.masharo.habits.domain.model.Habit
import kotlinx.coroutines.flow.Flow

class GetAllHabitsUseCase(private val dbRepository: DBHabitRepository) {

    fun execute(): Flow<List<Habit>> =
        dbRepository.getAllHabits()

}