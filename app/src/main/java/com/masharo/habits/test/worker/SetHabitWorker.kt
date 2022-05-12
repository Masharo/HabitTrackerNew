package com.masharo.habits.test.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.masharo.habits.data.HabitRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SetHabitWorker(context: Context, workerParams: WorkerParameters):
    CoroutineWorker(context, workerParams), KoinComponent {

    private val repository: HabitRepository by inject()
    override suspend fun doWork(): Result {

        val id = inputData.getInt(HABIT_ID, -1)

        if (id != -1) {
            repository.getHabit(id)?.let { habit ->
                repository.addHabitRemote(habit)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ listHabitRemote ->
                        Log.i("myLog", listHabitRemote.toString())
                    }, {
                        Log.i("myLog", it.toString())
                    })
            }
        }


        return Result.success()
    }
}