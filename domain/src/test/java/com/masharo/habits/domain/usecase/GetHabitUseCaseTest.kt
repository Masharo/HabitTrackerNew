package com.masharo.habits.domain.usecase

import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id
import com.masharo.habits.domain.repository.DBHabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub


class GetHabitUseCaseTest {

    private val dbRepository = mock<DBHabitRepository>()
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    @AfterEach
    fun tearDown() {
        Mockito.reset(dbRepository)
    }

    @Test
    fun shouldReturnCorrectData() {

        val testHabit = Habit(
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
        )

        dbRepository.stub {
            onBlocking {
                getHabit(Id(10))
            }.thenReturn(testHabit)
        }


        val useCase = GetHabitUseCase(dbRepository = dbRepository, dispatcher)

        val expected = runBlocking { useCase.execute(Id(10)) }
        val actual = Habit(
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
        )

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnCorrectDataNull() {

        val testHabit = null

        dbRepository.stub {
            onBlocking {
                getHabit(Id(100))
            }.thenReturn(testHabit)
        }


        val useCase = GetHabitUseCase(dbRepository = dbRepository, dispatcher)

        val expected = runBlocking { useCase.execute(Id(100)) }
        val actual = null

        assertEquals(expected, actual)
    }

}