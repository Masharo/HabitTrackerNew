package com.masharo.habits.data.remote.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.masharo.habits.data.database.HabitDatabase
import com.masharo.habits.data.remote.HabitApi
import com.masharo.habits.data.remote.HabitRemote
import com.masharo.habits.data.remoteToDataHabit

class UpdateAllHabitWorker(context: Context, workerParams: WorkerParameters):
    CoroutineWorker(context, workerParams) {

    private val db: HabitDatabase = HabitDatabase.instance(context)
    private val api: HabitApi = HabitRemote.getApi()

    override suspend fun doWork(): Result {

        val response = api.getHabits()

        if (response.isSuccessful) {
            response.body()?.let {
                db.getHabitDao().deleteAll()
                db.getHabitDao().addAll(*it.map { habit ->  remoteToDataHabit(habit) }.toTypedArray())
            }
        }

        response.errorBody()?.let {
            return Result.retry()
        }

        return Result.success()
    }
}