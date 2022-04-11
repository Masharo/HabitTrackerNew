package com.masharo.habits.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.masharo.habits.Habit

@Database(entities = [Habit::class], version = 1, exportSchema = false)
abstract class HabitDatabase: RoomDatabase() {

    companion object {
        const val NAME_DB = "habit_db"
        private var db: HabitDatabase? = null

        fun instance(context: Context): HabitDatabase {
            if (db == null) {
                @Synchronized
                if (db == null) {
                    db = Room.databaseBuilder(context, HabitDatabase::class.java, NAME_DB).build()
                }
            }

            return db!!
        }
    }

    abstract fun getHabitDao(): HabitDao
}