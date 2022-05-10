package com.masharo.habits.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "synchronyzed")
data class SynchronizedHabit(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val operation: Int
)

enum class SynchronizedOperation(val id: Int) {
    ADD(0),
    SET(1),
    GONE(2)
}