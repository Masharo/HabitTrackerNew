package com.masharo.habits.data

import androidx.lifecycle.LiveData
import com.masharo.habits.data.db.HabitDatabase
import com.masharo.habits.data.db.model.Habit
import com.masharo.habits.data.remote.HabitApi
import com.masharo.habits.data.remote.model.*
import retrofit2.Call
import retrofit2.Response

class HabitRepositoryImpl(
    val db: HabitDatabase,
    private val api: HabitApi
): HabitRepository {

    override fun getHabits(): LiveData<List<Habit>> {
        return db.getHabitDao().getAll()
    }

    override suspend fun getRemoteHabits(): Response<List<HabitRemote>> {
        return api.getHabits()
    }

    override fun addAll(habits: List<Habit>) {
        db.getHabitDao().addAll(habits)
    }

    override fun clearHabits(): Boolean {
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

    override suspend fun setHabitRemote(habit: Habit): Response<PutResult> {
        return api.setHabit(convertToHabitRemote(habit))
    }

    override suspend fun getHabit(id: Int): Habit? {
        return db.getHabitDao().get(id)
    }

    override suspend fun addHabit(habit: Habit): Long {
        return db.getHabitDao().add(habit)
    }

    override suspend fun addHabitRemote(habit: Habit): Response<PutResult> {
        return api.addHabit(convertToNewHabitRemote(habit))
    }

    override fun doneHabit(params: DoneParams): Call<Void> {
        TODO("Not yet implemented")
    }

}