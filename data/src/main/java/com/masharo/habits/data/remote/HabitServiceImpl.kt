package com.masharo.habits.data.remote

import android.content.Context
import androidx.work.*
import com.masharo.habits.data.HABIT_ID
import com.masharo.habits.data.model.local.IdData
import com.masharo.habits.data.model.remote.ParamHabitPutRemote
import com.masharo.habits.data.remote.worker.*
import java.util.concurrent.TimeUnit

class HabitServiceImpl(
    private val context: Context
): HabitService {

    override suspend fun incGoneCountHabitRemote(param: IdData) {
        startWorker(
            addIdParam(
                createBuilder<IncDoneCountHabitWorker>(),
                param.id
            )
        )
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

    override suspend fun deleteHabitRemote(param: IdData) {
        startWorker(
            addIdRemoteParam(
                createBuilder<DeleteHabitWorker>(),
                param.idRemote
            )
        )
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

    private fun addIdRemoteParam(request: OneTimeWorkRequest.Builder, id: String?) = request
        .setInputData(
            Data
                .Builder()
                .putString(HABIT_ID, id)
                .build()
        )


    private fun startWorker(request: OneTimeWorkRequest.Builder) =
        WorkManager
            .getInstance(context)
            .enqueue(request.build())
}