package com.masharo.habits.data.habitList

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.masharo.habits.data.habit.Habit
import com.masharo.habits.data.HabitDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomDataHabitList(val db: HabitDatabase): DataLogicHabitList {

    override suspend fun getHabits(): LiveData<List<Habit>> {
            return db.getHabitDao().habits()
    }

//    suspend fun getHabitsTask(): List<Habit> {
//        return db.getHabitDao().habits()
//    }

//    class HabitsTask(val db: HabitDatabase): AsyncTask<Void, Void, LiveData<List<Habit>>>() {
//        override fun doInBackground(vararg params: Void?): LiveData<List<Habit>> {
//            return db.getHabitDao().habits()
//        }
//    }

    class HabitsTypeFilterTask(val db: HabitDatabase):
        AsyncTask<Habit.TypeHabit, Void, LiveData<List<Habit>>>() {

        override fun doInBackground(vararg params: Habit.TypeHabit): LiveData<List<Habit>> {
            if (params.isNotEmpty()) {
                params[0]?.let {
                    return db.getHabitDao().habitsTypeFilter(it.id())
                }
            }

            throw Exception("HabitsTypeFilterTask, param = null or empty")
        }
    }
}