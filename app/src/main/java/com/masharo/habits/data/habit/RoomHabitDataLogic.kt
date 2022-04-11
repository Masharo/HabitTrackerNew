package com.masharo.habits.data.habit

import android.os.AsyncTask
import com.masharo.habits.data.HabitDatabase

class RoomHabitDataLogic(val db: HabitDatabase): DataLogicHabit {

    override fun setHabit(habit: Habit) {
        SetTask(db).execute(habit)
    }

    override fun getHabit(id: Int?): Habit? {
        return GetTask(db).execute(id).get()
    }

    override fun addHabit(habit: Habit) {
        AddTask(db).execute(habit)
    }

    class GetTask(val db: HabitDatabase): AsyncTask<Int, Void, Habit?>() {
        override fun doInBackground(vararg params: Int?): Habit? {
            if (params.isNotEmpty()) {
                params[0]?.let {
                    return db.getHabitDao().get(it)
                }
            }

            return null
        }
    }

    class AddTask(val db: HabitDatabase): AsyncTask<Habit, Void, Void>() {
        override fun doInBackground(vararg params: Habit?): Void? {
            if (params.isNotEmpty()) {
                params[0]?.let {
                    db.getHabitDao().add(it)
                }
            }

            return null
        }
    }

    class SetTask(val db: HabitDatabase): AsyncTask<Habit, Void, Void>() {
        override fun doInBackground(vararg params: Habit?): Void? {
            if (params.isNotEmpty()) {
                params[0]?.let {
                    db.getHabitDao().set(it)
                }
            }

            return null
        }
    }
}