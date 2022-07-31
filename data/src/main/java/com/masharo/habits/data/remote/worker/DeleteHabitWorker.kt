package com.masharo.habits.data.remote.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.masharo.habits.data.HABIT_ID
import com.masharo.habits.data.database.HabitDatabase
import com.masharo.habits.data.model.remote.IdRemote
import com.masharo.habits.data.remote.HabitApi
import com.masharo.habits.data.remote.HabitRemote

class DeleteHabitWorker(context: Context, workerParams: WorkerParameters):
    CoroutineWorker(context, workerParams) {

    private val db: HabitDatabase = HabitDatabase.instance(context)
    private val api: HabitApi = HabitRemote.getApi()

    override suspend fun doWork(): Result {
        inputData.getString(HABIT_ID)?.let {
            val result = api.deleteHabit(
                IdRemote(it)
            )

            if (result.isSuccessful) return Result.success()
        }

        return Result.failure()
    }

}