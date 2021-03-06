package com.masharo.habits.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitData(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("_id_")
    val id: Int? = null,

    @ColumnInfo("_id_remote")
    var idRemote: String?,

    @ColumnInfo("date_remote")
    val dateRemote: Int,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("description")
    val description: String,

    @ColumnInfo("priority")
    val priority: Int,

    @ColumnInfo("type")
    val type: Int,

    @ColumnInfo("count")
    val count: Int,

    @ColumnInfo("period")
    val period: Int,

    @ColumnInfo("count_done")
    var countDone: Int,

    @ColumnInfo("color")
    val color: Int

)
