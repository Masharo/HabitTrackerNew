package com.masharo.habits

import androidx.annotation.IdRes
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "habit")
class Habit : BaseObservable() {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @get:Bindable
    var title: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    @get:Bindable
    var description: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.description)
        }

    @get:Bindable
    var priority: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.priority)
        }

    @get:Bindable
    var type: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }

    @get:Bindable
    var count: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.count)
        }

    @get:Bindable
    var period: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.period)
        }
    @get:Bindable
    var countDone: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.countDone)
        }
    @get:Bindable
    var color: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.color)
        }

    @Ignore
    @get:Bindable
    var image: Int? = getTypeEnum().resourceDrawable()
        set(value) {
            field = value
            notifyPropertyChanged(BR.image)
        }

    fun getTypeEnum():TypeHabit {
        val typeHabit = TypeHabit.values()[type]
        this.image = typeHabit.resourceDrawable()
        return typeHabit
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