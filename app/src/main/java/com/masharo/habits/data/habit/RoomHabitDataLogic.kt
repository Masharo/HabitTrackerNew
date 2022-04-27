package com.masharo.habits.data.habit

import com.masharo.habits.data.HabitDatabase

class RoomHabitDataLogic(val db: HabitDatabase): DataLogicHabit {

    override suspend fun setHabit(habit: Habit) {
        db.getHabitDao().set(habit)
    }

    override suspend fun getHabit(id: Int?): Habit? {
        return id?.let {
            db.getHabitDao().get(id)
        }
    }

    override suspend fun addHabit(habit: Habit) {
        db.getHabitDao().add(habit)
    }

//    class GetTask(val db: HabitDatabase): AsyncTask<Int, Void, Habit?>() {
//        override fun doInBackground(vararg params: Int?): Habit? {
//            if (params.isNotEmpty()) {
//                params[0]?.let {
//                    return db.getHabitDao().get(it)
//                }
//            }
//
//            return null
//        }
//    }
//
//    class AddTask(val db: HabitDatabase): AsyncTask<Habit, Void, Void>() {
//        override fun doInBackground(vararg params: Habit?): Void? {
//            if (params.isNotEmpty()) {
//                params[0]?.let {
//                    db.getHabitDao().add(it)
//                }
//            }
//
//            return null
//        }
//    }
//
//    class SetTask(val db: HabitDatabase): AsyncTask<Habit, Void, Void>() {
//        override fun doInBackground(vararg params: Habit?): Void? {
//            if (params.isNotEmpty()) {
//                params[0]?.let {
//                    db.getHabitDao().set(it)
//                }
//            }
//
//            return null
//        }
//    }
}