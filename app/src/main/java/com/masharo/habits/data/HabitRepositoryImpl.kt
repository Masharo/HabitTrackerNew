package com.masharo.habits.data

import androidx.lifecycle.LiveData
import com.masharo.habits.data.db.HabitDatabase
import com.masharo.habits.data.db.model.Habit
import com.masharo.habits.data.remote.HabitApi
import com.masharo.habits.data.remote.model.DoneParams
import com.masharo.habits.data.remote.model.HabitRemote
import retrofit2.Call

class HabitRepositoryImpl(
    val db: HabitDatabase,
    private val api: HabitApi
): HabitRepository {

    override fun getHabits(): LiveData<List<Habit>> {
        return db.getHabitDao().getAll()
    }

    override fun getRemoteHabits(): Call<List<HabitRemote>> {
        return api.getHabits()
    }

    override suspend fun addAll(habits: List<Habit>) {
        db.getHabitDao().addAll(habits)
    }

//    override suspend fun updateHabits(): Boolean {
//
//        db.getHabitDao().deleteAll()
//
//        api.getHabits().enqueue(object: Callback<List<HabitRemote>> {
//            override fun onResponse(
//                call: Call<List<HabitRemote>>,
//                response: Response<List<HabitRemote>>
//            ) {
//                if (response.isSuccessful) {
//                    response.body()?.map { Habit() }?.let {
//                        db.getHabitDao().addAll(it)
//                    }
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
//        val execute = api.getHabits().execute()
//        execute.body()?.map { Habit() }?.let {
//            db.getHabitDao().addAll(it)
//        }
//

//        if (clearHabits()) {
//            request.body()?.let {
//                it.forEach {
//
//                }
//            }
//
//            request.errorBody()?.let {
//
//            }
//        }
//
//        return false
//
//    }

    override suspend fun clearHabits(): Boolean {
        return try {
            db.getHabitDao().deleteAll()
            true
        } catch (ex: Exception) {
            false
        }
    }

    override suspend fun setHabit(habit: Habit) {
        db.getHabitDao().set(habit)
    }

    override suspend fun getHabit(id: Int): Habit? {
        return db.getHabitDao().get(id)
    }

    override suspend fun addHabit(habit: Habit) {
        db.getHabitDao().add(habit)
    }

    override fun doneHabit(params: DoneParams): Call<Void> {
        TODO("Not yet implemented")
    }

}