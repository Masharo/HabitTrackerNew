package com.masharo.habits.domain.usecase

import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.repository.DBHabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetAllHabitsUseCaseTest{

    private val dbRepository = mock<DBHabitRepository>()

    @AfterEach
    fun tearDown() {
        Mockito.reset(dbRepository)
    }

    @Test
    fun shouldReturnCorrectData() {

        val testHabit = flowOf(
            listOf(
                Habit(
                    id = 10,
                    idRemote = null,
                    dateRemote = 123456,
                    title = "Test1",
                    description = "Test2",
                    priority = 1,
                    type = 0,
                    count = 11,
                    period = 2,
                    countDone = 5,
                    color = -1026
                ),

                Habit(
                    id = 1,
                    idRemote = "null",
                    dateRemote = 12345536,
                    title = "Test3",
                    description = "Test4",
                    priority = 0,
                    type = 0,
                    count = 12,
                    period = 2,
                    countDone = 5,
                    color = -3056
                )
            )

        )

        Mockito.`when`(dbRepository.getAllHabits()).thenReturn(testHabit)

        val useCase = GetAllHabitsUseCase(dbRepository = dbRepository)

        val expected = runBlocking { useCase.execute().first() }
        val actual = listOf(
            Habit(
                id = 10,
                idRemote = null,
                dateRemote = 123456,
                title = "Test1",
                description = "Test2",
                priority = 1,
                type = 0,
                count = 11,
                period = 2,
                countDone = 5,
                color = -1026
            ),

            Habit(
                id = 1,
                idRemote = "null",
                dateRemote = 12345536,
                title = "Test3",
                description = "Test4",
                priority = 0,
                type = 0,
                count = 12,
                period = 2,
                countDone = 5,
                color = -3056
            )
        )

        Assert.assertEquals(expected, actual)
    }

}