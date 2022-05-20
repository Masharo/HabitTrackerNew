package com.masharo.habits.dataNew.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "habits")
data class HabitData(

    @ColumnInfo("_id_")
    val id: Int? = null,

    @ColumnInfo("_id_remote")
    val idRemote: String,

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
    val countDone: Int,

    @ColumnInfo("color")
    val color: Int

)
