package com.masharo.habits.data.remote.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.masharo.habits.data.HABIT_ID
import com.masharo.habits.data.dataToRemoteHabit
import com.masharo.habits.data.database.HabitDatabase
import com.masharo.habits.data.remote.HabitApi
import com.masharo.habits.data.remote.HabitRemote

class AddHabitWorker(context: Context, workerParams: WorkerParameters):
    CoroutineWorker(context, workerParams) {

    private val db: HabitDatabase = HabitDatabase.instance(context)
    private val api: HabitApi = HabitRemote.getApi()

    override suspend fun doWork(): Result {

        val id = inputData.getInt(HABIT_ID, -1)

        if (id != -1) {
            db.getHabitDao().get(id)?.let { habit ->

                val response = api.putHabit(dataToRemoteHabit(habit))
                response.body()?.let {
                    habit.idRemote = it.id
                    db.getHabitDao().set(habit)
                }

                response.errorBody()?.let {
                    return Result.retry()
                }

            }
        }

        return Result.success()
    }
}