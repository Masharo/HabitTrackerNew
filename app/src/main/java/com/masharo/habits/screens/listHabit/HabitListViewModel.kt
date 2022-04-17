package com.masharo.habits.screens.listHabit

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import com.masharo.habits.adapter.HabitDiffUtilCallback
import com.masharo.habits.adapter.HabitsAdapter
import com.masharo.habits.data.HabitDatabase
import com.masharo.habits.data.habit.Habit
import com.masharo.habits.data.habitList.HabitListFilter
import com.masharo.habits.data.habitList.RoomDataHabitList
import com.masharo.habits.databinding.FragmentHabitListBinding

class HabitListViewModel(val context: Context): ViewModelProvider.Factory {

    private var habitListFilter: HabitListFilter = HabitListFilter()
    private val db = RoomDataHabitList(HabitDatabase.instance(app.applicationContext))
    lateinit var habits: LiveData<List<Habit>>
    lateinit var adapter: HabitsAdapter

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Context::class.java).newInstance(context)
    }

    fun instance(
        bind: FragmentHabitListBinding,
        owner: LifecycleOwner,
    ) {
        bind.viewModel = this

        habits = db.getHabits()

        adapter = bind.recyclerViewHabitsListHabits.adapter as HabitsAdapter

        habitListFilter.getHabits().observe(owner) {
            habitListChange(it)
        }

        habits.observe(owner) {
            habitListFilter.habitsOrigin = it
        }
    }

    private fun habitListChange(list: List<Habit>) {
        val habitDiffUtilCallback = HabitDiffUtilCallback(adapter.habits ?: arrayListOf(), list)
        val resultDiff = DiffUtil.calculateDiff(habitDiffUtilCallback)
        adapter.habits = list
        resultDiff.dispatchUpdatesTo(adapter)
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

    fun removeHabit(id: Int) {
    }

    fun swapHabit(id1: Int, id2: Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HabitListViewModel> {
        override fun createFromParcel(parcel: Parcel): HabitListViewModel {
            return HabitListViewModel(parcel)
        }

        override fun newArray(size: Int): Array<HabitListViewModel?> {
            return arrayOfNulls(size)
        }
    }

}