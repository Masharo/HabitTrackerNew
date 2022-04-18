package com.masharo.habits.data.habitList

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.masharo.habits.data.habit.Habit
import com.masharo.habits.data.HabitDatabase

class RoomDataHabitList(val db: HabitDatabase): DataLogicHabitList {

    override fun getHabits(): LiveData<List<Habit>> {
        return HabitsTask(db).execute().get()
    }

    override fun getHabitsTypeFilter(type: Habit.TypeHabit): LiveData<List<Habit>> {
        return HabitsTypeFilterTask(db).execute(type).get()
    }

    class HabitsTask(val db: HabitDatabase): AsyncTask<Void, Void, LiveData<List<Habit>>>() {
        override fun doInBackground(vararg params: Void?): LiveData<List<Habit>> {
            return db.getHabitDao().habits()
        }
    }

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