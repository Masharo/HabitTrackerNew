package com.masharo.habits.dataNew.remote.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.masharo.habits.dataNew.database.HabitDatabase
import com.masharo.habits.dataNew.remote.HabitApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UpdateAllHabitWorker(context: Context, workerParams: WorkerParameters):
    CoroutineWorker(context, workerParams), KoinComponent {

    private val db: HabitDatabase by inject()
    private val api: HabitApi by inject()

    override suspend fun doWork(): Result {

        db.getHabitDao().deleteAll()
        val response = api.getHabits()

        if (response.isSuccessful) {
            response.body()?.let {
                db.getHabitDao().addAll(*it.toTypedArray())
            }
        }

        response.errorBody()?.let {
            return Result.retry()
        }

        return Result.success()
    }
}