package com.masharo.habits.dataNew.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "habits")
data class HabitData(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("_id_")
    val id: Int? = null,

    @ColumnInfo("_id_remote")
    @SerializedName("uid")
    var idRemote: String?,

    @ColumnInfo("date_remote")
    @SerializedName("date")
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
    @SerializedName("frequency")
    val period: Int,

    @ColumnInfo("count_done")
    val countDone: Int,

    @ColumnInfo("color")
    val color: Int

)
