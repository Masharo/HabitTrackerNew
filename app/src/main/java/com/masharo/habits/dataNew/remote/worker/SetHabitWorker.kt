package com.masharo.habits.dataNew.remote.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.masharo.habits.HABIT_ID
import com.masharo.habits.data.HabitRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SetHabitWorker(context: Context, workerParams: WorkerParameters):
    CoroutineWorker(context, workerParams), KoinComponent {

    private val repository: HabitRepository by inject()
    override suspend fun doWork(): Result {

        val id = inputData.getInt(HABIT_ID, -1)

        if (id != -1) {
            repository.getHabit(id)?.let { habit ->
                if (habit.idRemote.isNotEmpty()) {

                    repository.setHabitRemote(habit).errorBody()?.let {
                        return Result.retry()
                    }

                }
            }
        }

        return Result.success()
    }
}
