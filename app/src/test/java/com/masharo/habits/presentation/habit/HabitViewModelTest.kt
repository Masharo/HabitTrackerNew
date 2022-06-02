package com.masharo.habits.presentation.habit

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id
import com.masharo.habits.domain.usecase.AddHabitUseCase
import com.masharo.habits.domain.usecase.EditHabitUseCase
import com.masharo.habits.domain.usecase.GetHabitUseCase
import com.masharo.habits.presentation.model.HabitPresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.stub

class HabitViewModelTest {

    private val getHabitUseCase = mock<GetHabitUseCase>()
    private val addHabitUseCase = mock<AddHabitUseCase>()
    private val editHabitUseCase = mock<EditHabitUseCase>()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @AfterEach
    fun afterEach() {
        reset(getHabitUseCase)
        reset(addHabitUseCase)
        reset(editHabitUseCase)
    }

    @BeforeEach
    fun beforeEach() {
        ArchTaskExecutor.getInstance().setDelegate(object: TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread() = true

        })
    }

    @Test
    fun setHabit() {

        val vm = HabitViewModel(
            getHabitUseCase,
            addHabitUseCase,
            editHabitUseCase
        )

        val paramId = 10

        val returnHabit = Habit(
            id = 10,
            idRemote = "12345",
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

        getHabitUseCase.stub {
            onBlocking {
                execute(Id(paramId))
            }.thenReturn(returnHabit)
        }

        Assertions.assertEquals(vm.habit.value, HabitPresentation())

        vm.setHabit(paramId)

        val expected = vm.habit.value
        val actual = HabitPresentation()
            .apply {
                id = 10
                title = "Test3"
                idRemote = "12345"
                description = "Test4"
                priority = 0
                type = 0
                color = -3056
                count = 12
                countDone = 5
            }

        Assertions.assertTrue(expected?.equals(actual) ?: false)
    }

    @Test
    fun save() {

    }

    @Test
    fun changeHabitColor() {

    }

}