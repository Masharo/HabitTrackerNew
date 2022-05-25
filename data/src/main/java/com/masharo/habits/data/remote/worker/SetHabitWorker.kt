package com.masharo.habits.data.remote.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.masharo.habits.data.HABIT_ID
import com.masharo.habits.data.dataToRemoteHabit
import com.masharo.habits.data.database.HabitDatabase
import com.masharo.habits.data.remote.HabitApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SetHabitWorker(context: Context, workerParams: WorkerParameters):
    CoroutineWorker(context, workerParams), KoinComponent {

    private val db: HabitDatabase by inject()
    private val api: HabitApi by inject()
    
    override suspend fun doWork(): Result {

        val id = inputData.getInt(HABIT_ID, -1)

        if (id != -1) {
            db.getHabitDao().get(id)?.let { habit ->
                habit.idRemote?.let {
                    api.putHabit(dataToRemoteHabit(habit)).errorBody()?.let {
                        return Result.retry()
                    }
                }
            }
        }

        return Result.success()
    }
}