package com.masharo.habits.presentation.listHabit

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.masharo.habits.data.HabitDatabase
import com.masharo.habits.data.model.Habit
import com.masharo.habits.data.HabitListFilter
import com.masharo.habits.data.HabitRepositoryImpl
import com.masharo.habits.data.remote.HabitApi
import com.masharo.habits.data.remote.model.HabitRemote
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HabitListViewModel(context: Context, private val remoteApi: HabitApi): ViewModel() {

    private var habitListFilter: HabitListFilter = HabitListFilter()
    private val db = HabitRepositoryImpl(HabitDatabase.instance(context))
    private val remoteHabitsMutable: MutableLiveData<List<HabitRemote>> = MutableLiveData()
//    private val calls = arrayListOf<Call<*>>()

    val remoteHabits: LiveData<List<HabitRemote>> = remoteHabitsMutable
    val habits: LiveData<List<Habit>> = db.getHabits()

    init {
        getRemoteHabitList()
    }

    private fun getRemoteHabitList() {

        val call = remoteApi.getHabits()
//        calls.add(call)

        call.enqueue(object: Callback<List<HabitRemote>> {
            override fun onResponse(
                call: Call<List<HabitRemote>>,
                response: Response<List<HabitRemote>>
            ) {
                if (response.isSuccessful) {
                    remoteHabitsMutable.value = response.body()
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
//
//    override fun onCleared() {
//        calls.forEach { it.cancel() }
//        super.onCleared()
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