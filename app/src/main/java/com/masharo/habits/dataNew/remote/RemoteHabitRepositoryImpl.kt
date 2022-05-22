package com.masharo.habits.dataNew.remote

import android.content.Context
import androidx.work.*
import com.masharo.habits.HABIT_ID
import com.masharo.habits.dataNew.remote.worker.AddHabitWorker
import com.masharo.habits.dataNew.remote.worker.SetHabitWorker
import com.masharo.habits.dataNew.remote.worker.UpdateAllHabitWorker
import com.masharo.habits.domain.RemoteHabitRepository
import com.masharo.habits.domain.model.Habit
import com.masharo.habits.domain.model.Id
import java.util.concurrent.TimeUnit

class RemoteHabitRepositoryImpl(
    private val api: HabitApi,
    private val context: Context
): RemoteHabitRepository {

    override suspend fun incGoneCountHabitRemote(id: Id) {
        TODO("Not yet implemented")
    }

    override suspend fun putHabitRemote(habit: Habit) {
        habit.id?.let {
            habit.idRemote?.apply {
                startWorker(
                    addIdParam(
                        createBuilder<SetHabitWorker>(),
                        it
                    )
                )
            } ?: run {
                startWorker(
                    addIdParam(
                        createBuilder<AddHabitWorker>(),
                        it
                    )
                )
            }
        }
    }

    override suspend fun deleteHabitRemote(id: Id) {
        TODO("Not yet implemented")
    }

    override suspend fun updateHabits() {
        startWorker(
            createBuilder<UpdateAllHabitWorker>()
        )
    }

    private inline fun <reified W : ListenableWorker>createBuilder() = OneTimeWorkRequestBuilder<W>()
        .setBackoffCriteria(
            BackoffPolicy.LINEAR,
            OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
            TimeUnit.MILLISECONDS)
        .setConstraints(
            Constraints
                .Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build())

    private fun addIdParam(request: OneTimeWorkRequest.Builder, id: Int) = request
        .setInputData(
            Data
                .Builder()
                .putInt(HABIT_ID, id)
                .build()
        )


    private fun startWorker(request: OneTimeWorkRequest.Builder) =
        WorkManager
            .getInstance(context)
            .enqueue(request.build())

}