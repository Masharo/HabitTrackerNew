package com.masharo.habits.presentation.listHabit

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.masharo.habits.data.HabitListFilter
import com.masharo.habits.data.HabitRepository
import com.masharo.habits.data.db.model.Habit
import com.masharo.habits.data.remote.worker.UpdateAllHabitWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class HabitListViewModel(
    val context: Context,
    private val repository: HabitRepository
): ViewModel() {

    private var habitListFilter: HabitListFilter = HabitListFilter()
    val habits: LiveData<List<Habit>> = repository.getHabits()

    init {
        viewModelScope.launch(Dispatchers.IO) {
        WorkManager
            .getInstance(context)
            .enqueue(
                OneTimeWorkRequestBuilder<UpdateAllHabitWorker>()
                    .setBackoffCriteria(
                        BackoffPolicy.LINEAR,
                        OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                        TimeUnit.MILLISECONDS)
                    .setConstraints(
                        Constraints
                            .Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()
                    ).build()
            )
        }
    }

    fun setFilterType(type: Int) {
        habitListFilter.setFilter(HabitListFilter.Column.TYPE, type)
    }

    fun setSort(column: HabitListFilter.Column) {
        habitListFilter.sortColumn = column
    }

    fun setSearch(search: String) {
        habitListFilter.search = search
    }

    fun getChangeHabits(): LiveData<List<Habit>> {
        return habitListFilter.getHabits()
    }

    fun setNewHabitList(habits: List<Habit>) {
        habitListFilter.habitsOrigin = habits
    }
}