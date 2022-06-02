package com.masharo.habits.domain.usecase

import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.repository.DBHabitRepository
import com.masharo.habits.domain.repository.RemoteHabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

class EditHabitUseCaseTest {

    private val dbRepository = mock<DBHabitRepository>()
    private val remoteRepository = mock<RemoteHabitRepository>()
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default

    @AfterEach
    fun tearDown() {
        Mockito.reset(dbRepository)
        Mockito.reset(remoteRepository)
    }

    @Test
    fun shouldReturnTrue() {

        val testParamHabit = Habit(
            id = null,
            idRemote = "",
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

        dbRepository.stub {
            onBlocking {
                editHabit(testParamHabit)
            }.doReturn(Unit)
        }

        remoteRepository.stub {
            onBlocking {
                putHabitRemote(testParamHabit)
            }.doReturn(Unit)
        }

        val useCase = EditHabitUseCase(
            dbRepository,
            remoteRepository,
            dispatcher
        )

        val expected = runBlocking { useCase.execute(testParamHabit) }
        val actual = true

        Assert.assertEquals(expected, actual)

    }

    @Test
    fun shouldReturnExceptionInDb() {

        val testParamHabit = Habit(
            id = null,
            idRemote = "",
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

        dbRepository.stub {
            onBlocking {
                editHabit(testParamHabit)
            }.thenThrow(RuntimeException())
        }

        val useCase = EditHabitUseCase(
            dbRepository,
            remoteRepository,
            dispatcher
        )

        val expected = runBlocking { useCase.execute(testParamHabit) }
        val actual = false

        Assert.assertEquals(expected, actual)

    }

    @Test
    fun shouldReturnExceptionInRemote() {

        val testParamHabit = Habit(
            id = null,
            idRemote = "",
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

        dbRepository.stub {
            onBlocking {
                editHabit(testParamHabit)
            }.thenReturn(Unit)
        }

        remoteRepository.stub {
            onBlocking {
                putHabitRemote(testParamHabit)
            }.thenThrow(RuntimeException())
        }

        val useCase = EditHabitUseCase(
            dbRepository,
            remoteRepository,
            dispatcher
        )

        val expected = runBlocking { useCase.execute(testParamHabit) }
        val actual = false

        Assert.assertEquals(expected, actual)

    }

}