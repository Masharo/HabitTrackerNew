package com.masharo.habits.data.remote.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.masharo.habits.data.HabitRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UpdateAllHabitWorker(context: Context, workerParams: WorkerParameters):
    CoroutineWorker(context, workerParams), KoinComponent {

    private val repository: HabitRepository by inject()

    override suspend fun doWork(): Result {

        repository.clearHabits()
        val response = repository.getRemoteHabits()

        if (response.isSuccessful) {
            response.body()?.let {
                repository.addAll(it.map { habit -> habit.convertToHabit() })
            }
        }

        response.errorBody()?.let {
            return Result.retry()
        }

        return Result.success()
    }
}