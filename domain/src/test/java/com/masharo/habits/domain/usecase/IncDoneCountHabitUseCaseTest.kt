package com.masharo.habits.domain.usecase

import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id
import com.masharo.habits.domain.repository.DBHabitRepository
import com.masharo.habits.domain.repository.RemoteHabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.mockito.Mockito
import org.mockito.kotlin.*

class IncDoneCountHabitUseCaseTest {

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

        val testParamId = Id(10)
        val testParamHabit = Habit(
            id = 10,
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
                dbRepository.getHabit(testParamId)
            }.doReturn(testParamHabit)

            onBlocking {
                dbRepository.editHabit(testParamHabit)
            }.doReturn(Unit)
        }

        remoteRepository.stub {
            onBlocking {
                incGoneCountHabitRemote(testParamId)
            }.doReturn(Unit)
        }

        val useCase = IncDoneCountHabitUseCase(
            dbRepository,
            remoteRepository,
            dispatcher
        )

        val expected = runBlocking { useCase.execute(testParamId) }
        val actual = true

        Assert.assertEquals(expected, actual)

    }

    @Test
    fun shouldReturnFalseHabitNotInDb() {

        val testParamId = Id(10)

        dbRepository.stub {
            onBlocking {
                dbRepository.getHabit(testParamId)
            }.doReturn(null)
        }

        val useCase = IncDoneCountHabitUseCase(
            dbRepository,
            remoteRepository,
            dispatcher
        )

        val expected = runBlocking { useCase.execute(testParamId) }
        val actual = false

        Assert.assertEquals(expected, actual)

        runBlocking {
            verify(dbRepository, never()).editHabit(any())
            verify(remoteRepository, never()).incGoneCountHabitRemote(any())
        }

    }

    @Test
    fun shouldReturnCount() {

        val testParamId = Id(10)
        val testParamHabit = Habit(
            id = 10,
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

        val testParamHabitIncCountDone = Habit(
            id = 10,
            idRemote = "",
            dateRemote = 12345536,
            title = "Test3",
            description = "Test4",
            priority = 0,
            type = 0,
            count = 12,
            period = 2,
            countDone = 6,
            color = -3056
        )

        dbRepository.stub {
            onBlocking {
                dbRepository.getHabit(testParamId)
            }.doReturn(testParamHabit)

            onBlocking {
                dbRepository.editHabit(testParamHabit)
            }.doReturn(Unit)
        }

        val useCase = IncDoneCountHabitUseCase(
            dbRepository,
            remoteRepository,
            dispatcher
        )

        runBlocking { useCase.execute(testParamId) }

        runBlocking {
            verify(dbRepository, times(1)).editHabit(testParamHabitIncCountDone)
        }
    }

}