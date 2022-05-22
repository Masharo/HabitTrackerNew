package com.masharo.habits.dataNew.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.masharo.habits.dataNew.model.local.HabitData

@Database(entities = [HabitData::class], version = 1, exportSchema = false)
abstract class HabitDatabase: RoomDatabase() {

    abstract fun getHabitDao(): HabitDao

    companion object {
        private const val NAME_DB = "habit_db"
        @Volatile
        private var db_local: HabitDatabase? = null
        private val db: HabitDatabase by lazy { db_local ?: throw Exception("Ошибка инициализации БД") }

        fun instance(context: Context): HabitDatabase {
            db_local ?: run {
                @Synchronized
                if (db_local == null) {
                    db_local = Room
                        .databaseBuilder(
                            context,
                            HabitDatabase::class.java,
                            NAME_DB
                        ).build()
                }
            }

            return db
        }
    }
}