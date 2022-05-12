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
                    .setConstraints(
                        Constraints
                            .Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()
                    ).build()
            )
        }
    }

//    init {
//        load()
//    }
//
//    fun load() {
//        compositeDisposable.add(
//            repository.getHabitsTest()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ listHabitRemote ->
////                    habitsR.postValue(listHabitRemote)
//                    Log.i("myLog", listHabitRemote.toString())
//                }, {
//                    Log.i("myLog", it.toString())
//                })
//        )
//    }
//    init {
//
//        repository.getRemoteHabits().enqueue(object: Callback<List<HabitRemote>> {
//            override fun onResponse(
//                call: Call<List<HabitRemote>>,
//                response: Response<List<HabitRemote>>
//            ) {
//                if (response.isSuccessful) {
//                    response.body()
//                        ?.map { it.convertToHabit() }
//                        ?.let {
//                            viewModelScope.launch(Dispatchers.IO) {
//                                repository.clearHabits()
//                                repository.addAll(it)
//                            }
//                        }
//                } else {
//                    Log.i("myLog", "err 5")
//                }
//            }
//
//            override fun onFailure(call: Call<List<HabitRemote>>, t: Throwable) {
//                t.message?.let {
//                    Log.i("myLog", it)
//                }
//            }
//        })
//
//    }

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