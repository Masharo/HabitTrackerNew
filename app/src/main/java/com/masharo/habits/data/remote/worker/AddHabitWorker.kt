package com.masharo.habits.data.remote.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.masharo.habits.HABIT_ID
import com.masharo.habits.data.HabitRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddHabitWorker(context: Context, workerParams: WorkerParameters):
    CoroutineWorker(context, workerParams), KoinComponent {

    private val repository: HabitRepository by inject()

    override suspend fun doWork(): Result {

        val id = inputData.getInt(HABIT_ID, -1)

        if (id != -1) {
            repository.getHabit(id)?.let { habit ->

                val response = repository.addHabitRemote(habit)
                response.body()?.let {
                    habit.idRemote = it.id
                    repository.setHabit(habit)
                }

                response.errorBody()?.let {
                    return Result.retry()
                }

            }
        }

        return Result.success()
    }
}