package com.masharo.habits.presentation.listHabit

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masharo.habits.data.HabitListFilter
import com.masharo.habits.data.HabitRepository
import com.masharo.habits.data.db.model.Habit
import com.masharo.habits.data.remote.model.HabitRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HabitListViewModel(
    context: Context,
    private val repository: HabitRepository
): ViewModel() {

    private var habitListFilter: HabitListFilter = HabitListFilter()
    val habits: LiveData<List<Habit>> = repository.getHabits()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearHabits()
        }

        repository.getRemoteHabits().enqueue(object: Callback<List<HabitRemote>> {
            override fun onResponse(
                call: Call<List<HabitRemote>>,
                response: Response<List<HabitRemote>>
            ) {
                if (response.isSuccessful) {
                    response.body()
                        ?.map { it.convertToHabit() }
                        ?.let {
                            viewModelScope.launch {
                                repository.addAll(it)
                            }
                        }
                } else {
                    Log.i("myLog", "err 5")
                }
            }

            override fun onFailure(call: Call<List<HabitRemote>>, t: Throwable) {
                t.message?.let {
                    Log.i("myLog", it)
                }
            }
        })

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