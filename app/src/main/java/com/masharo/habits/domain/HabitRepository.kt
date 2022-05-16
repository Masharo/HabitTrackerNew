package com.masharo.habits.domain

import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.input.InputDataDelete
import com.masharo.habits.domain.model.input.InputDataGet
import com.masharo.habits.domain.model.input.InputDataGone
import com.masharo.habits.domain.model.output.OutputDataAdd
import com.masharo.habits.domain.model.output.OutputDataDelete
import com.masharo.habits.domain.model.output.OutputDataGone
import com.masharo.habits.domain.model.output.OutputLoadHabits
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun putHabit(habit: Habit): OutputDataAdd
    fun incGoneCountHabit(inputDataGone: InputDataGone): OutputDataGone
    fun deleteHabit(inputDataDelete: InputDataDelete): OutputDataDelete
    fun getHabit(inputDataGet: InputDataGet): Habit?
    fun getAllHabits(): Flow<List<Habit>>
    fun loadHabits(): OutputLoadHabits
}