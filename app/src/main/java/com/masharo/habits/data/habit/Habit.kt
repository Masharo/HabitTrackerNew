package com.masharo.habits.data.habit

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.masharo.habits.BR
import com.masharo.habits.R

@Entity(tableName = "habit")
class Habit : BaseObservable() {

    companion object {
        const val DB_ID = "_id_"
        const val DB_TITLE = "title"
        const val DB_DESCRIPTION = "description"
        const val DB_PRIORITY = "priority"
        const val DB_TYPE = "type"
        const val DB_COUNT = "count"
        const val DB_PERIOD = "period"
        const val DB_COUNT_DONE = "count_done"
        const val DB_COLOR = "color"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DB_ID)
    var id: Int? = null

    @get:Bindable
    @ColumnInfo(name = DB_TITLE)
    var title: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    @ColumnInfo(name = DB_DESCRIPTION)
    var description: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.description)
        }

    @get:Bindable
    @ColumnInfo(name = DB_PRIORITY)
    var priority: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.priority)
        }

    @get:Bindable
    @ColumnInfo(name = DB_TYPE)
    var type: Int = 0
        set(value) {
            field = value
            image = getTypeEnum().resourceDrawable()
            notifyPropertyChanged(BR.type)
        }

    @get:Bindable
    @ColumnInfo(name = DB_COUNT)
    var count: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.count)
        }

    @get:Bindable
    @ColumnInfo(name = DB_PERIOD)
    var period: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.period)
        }
    @get:Bindable
    @ColumnInfo(name = DB_COUNT_DONE)
    var countDone: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.countDone)
        }
    @get:Bindable
    @ColumnInfo(name = DB_COLOR)
    var color: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.color)
        }

    @Ignore
    @get:Bindable
    var image: Int = getTypeEnum().resourceDrawable()
        set(value) {
            field = value
            notifyPropertyChanged(BR.image)
        }

    fun getTypeEnum(): TypeHabit {
        return TypeHabit.values()[type]
    }

    fun setTypeEnum(type: TypeHabit) {
        this.image = type.resourceDrawable()
        this.type = type.id()
    }

    enum class TypeHabit {
        POSITIVE {
            override fun resourceString(): Int =
                R.string.habit_type_positive

            override fun resourceDrawable(): Int =
                R.drawable.ic_smailhappy

            override fun id(): Int = 0
        },
        NEGATIVE {
            override fun resourceString(): Int =
                R.string.habit_type_negative

            override fun resourceDrawable(): Int =
                R.drawable.ic_smailsad

            override fun id(): Int = 1
        };

        abstract fun resourceString(): Int
        abstract fun resourceDrawable(): Int
        abstract fun id(): Int

        companion object {
            fun searchForResource(id: Int): Int {
               return values().filter { it.resourceString() == id }[0].id()
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Habit

        if (id != other.id) return false
        if (title != other.title) return false
        if (description != other.description) return false
        if (priority != other.priority) return false
        if (type != other.type) return false
        if (count != other.count) return false
        if (period != other.period) return false
        if (countDone != other.countDone) return false
        if (color != other.color) return false

        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}