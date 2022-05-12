package com.masharo.habits.presentation.listHabit

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.masharo.habits.data.HabitListFilter
import com.masharo.habits.data.HabitRepository
import com.masharo.habits.data.db.model.Habit
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HabitListViewModel(
    context: Context,
    private val repository: HabitRepository
): ViewModel() {

    private var habitListFilter: HabitListFilter = HabitListFilter()
    val habits: LiveData<List<Habit>> = repository.getHabits()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val habitsR = MutableLiveData<List<Habit>>()

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