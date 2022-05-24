package com.masharo.habits.dataNew.remote

import android.content.Context
import androidx.work.*
import com.masharo.habits.HABIT_ID
import com.masharo.habits.dataNew.model.local.IdData
import com.masharo.habits.dataNew.model.remote.ParamHabitPutRemote
import com.masharo.habits.dataNew.remote.worker.AddHabitWorker
import com.masharo.habits.dataNew.remote.worker.SetHabitWorker
import com.masharo.habits.dataNew.remote.worker.UpdateAllHabitWorker
import java.util.concurrent.TimeUnit

class HabitServiceImpl(
    private val context: Context
): HabitService {

    override suspend fun incGoneCountHabitRemote(id: IdData) {
        TODO("Not yet implemented")
    }

    override suspend fun putHabitRemote(param: ParamHabitPutRemote) {
        param.idData?.let {
            param.idRemote?.apply {
                startWorker(
                    addIdParam(
                        createBuilder<SetHabitWorker>(),
                        param.idData
                    )
                )
            } ?: {
                startWorker(
                    addIdParam(
                        createBuilder<AddHabitWorker>(),
                        param.idData
                    )
                )
            }
        }
    }

    override suspend fun deleteHabitRemote(id: IdData) {
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