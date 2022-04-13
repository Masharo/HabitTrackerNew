package com.masharo.habits.data.habitList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.masharo.habits.R
import com.masharo.habits.data.habit.Habit
import kotlin.reflect.KProperty1

//Кароч у класса должено быть состояние, мы будем вызывать один метод который будет принимать лист
class HabitListFilter {

    var habitsOrigin: List<Habit> = listOf()
        set(value) {
            field = value
            habits.value = value
        }

    private lateinit var habits: MutableLiveData<List<Habit>>

    var column: Column = Column.ID

    private var filterColumn: Column = Column.TYPE
    private var filterValue: Int = 0

    var sortColumn: Column = Column.ID
        set(value) {
            field = value
            update()
        }

    var search: String = ""
        set(value) {
            field = value
            update()
        }

    fun setFilter(column: Column, value: Int) {
        filterColumn = column
        filterValue = value

        update()
    }

    private fun update() {
        habits.value = sort(search(filter(habitsOrigin)))
    }

    private fun sort(habitsLocal: List<Habit>): List<Habit> = habitsLocal.sortedBy {
            column.getColumn()?.invoke(it)
        }

    private fun filter(habitsLocal: List<Habit>): List<Habit> {
        if (search.isEmpty()) {
            habitsLocal
        } else {
            habitsLocal.filter {
                Habit::title.invoke(it).contains(search, true)
            }
//            habits.filter { habit ->
//                columnsLike.map { it.getColumnString()?.invoke(habit) }.contains(search)
//            }
        }

        habitsLocal.filter {
            filterColumn.getColumn()?.invoke(it) == filterValue
        }
    }

    private fun search(habitsLocal: List<Habit>): List<Habit> =
        if (search.isEmpty()) {
            habitsLocal
        } else {
            habitsLocal.filter {
                Habit::title.invoke(it).contains(search, true)
            }
//            habits.filter { habit ->
//                columnsLike.map { it.getColumnString()?.invoke(habit) }.contains(search)
//            }
        }

    
    companion object {

        val columnsOrderBy = useOperation(Operation.ORDER_BY)
        val columnsLike = useOperation(Operation.LIKE)

        fun useOperation(operation: Operation): List<Column> {
            return Column.values().filter { operation.validOperationColumn(it) }
        }

        fun useOperationToStringResource(operation: Operation): List<Int> {
            return Column.values().filter { operation.validOperationColumn(it) }
                .map { it.getResource() }
        }

        fun useOperationToString(operation: Operation, context: Context): List<String> {
            return Column.values().filter { operation.validOperationColumn(it) }
                .map { context.getString(it.getResource()) }
        }
    }

    enum class Sort {
        DESC {
            override fun getResource(): Int = R.string.db_desc

            override fun toString(): String {
                return "DESC"
            }
        },
        ASC {
            override fun getResource(): Int = R.string.db_asc

            override fun toString(): String {
                return "ASC"
            }
        };

        abstract fun getResource(): Int
    }

    enum class Operation {
        LIKE {
            override fun getResource(): Int = R.string.db_like
            override fun validOperationColumn(column: Column): Boolean = column.useLike()

            override fun toString(): String {
                return "LIKE"
            }
        },
        ORDER_BY {
            override fun getResource(): Int = R.string.db_order
            override fun validOperationColumn(column: Column): Boolean = column.useOrderBy()

            override fun toString(): String {
                return "ORDER BY"
            }
        };

        abstract fun getResource(): Int
        abstract fun validOperationColumn(column: Column): Boolean
    }

    enum class Column {
        ID {
            override fun getResource(): Int {
               return R.string.db_id
            }

            override fun useLike(): Boolean = false
            override fun useOrderBy(): Boolean = true
            override fun getColumn(): KProperty1<Habit, Int?> = Habit::id
            override fun getColumnString(): KProperty1<Habit, String?>? = null
            override fun toString(): String = Habit.DB_ID

        },
        TITLE {
            override fun getResource(): Int {
                return R.string.db_title
            }

            override fun useLike(): Boolean = true
            override fun useOrderBy(): Boolean = false
            override fun getColumn(): KProperty1<Habit, Int>? = null
            override fun getColumnString(): KProperty1<Habit, String?> = Habit::title
            override fun toString(): String = Habit.DB_TITLE
        },
        DESCRIPTION {
            override fun getResource(): Int {
                return R.string.db_description
            }

            override fun useLike(): Boolean = false
            override fun useOrderBy(): Boolean = false
            override fun getColumn(): KProperty1<Habit, Int>? = null
            override fun getColumnString(): KProperty1<Habit, String?> = Habit::description
            override fun toString(): String = Habit.DB_DESCRIPTION
        },
        TYPE {
            override fun getResource(): Int {
                return R.string.db_type
            }

            override fun useLike(): Boolean = false
            override fun useOrderBy(): Boolean = false
            override fun getColumn(): KProperty1<Habit, Int> = Habit::type
            override fun getColumnString(): KProperty1<Habit, String?>? = null
            override fun toString(): String = Habit.DB_TYPE
        },
        COUNT {
            override fun getResource(): Int {
                return R.string.db_count
            }

            override fun useLike(): Boolean = false
            override fun useOrderBy(): Boolean = true
            override fun getColumn(): KProperty1<Habit, Int> = Habit::count
            override fun getColumnString(): KProperty1<Habit, String?>? = null
            override fun toString(): String = Habit.DB_COUNT
        },
        PERIOD {
            override fun getResource(): Int {
                return R.string.db_period
            }

            override fun useLike(): Boolean = false
            override fun useOrderBy(): Boolean = true
            override fun getColumn(): KProperty1<Habit, Int> = Habit::period
            override fun getColumnString(): KProperty1<Habit, String?>? = null
            override fun toString(): String = Habit.DB_PERIOD
        },
        COUNT_DONE {
            override fun getResource(): Int {
                return R.string.db_count_done
            }

            override fun useLike(): Boolean = false
            override fun useOrderBy(): Boolean = true
            override fun getColumn(): KProperty1<Habit, Int> = Habit::countDone
            override fun getColumnString(): KProperty1<Habit, String?>? = null
            override fun toString(): String = Habit.DB_COUNT_DONE
        };

        abstract fun getResource(): Int
        abstract fun useLike(): Boolean
        abstract fun useOrderBy(): Boolean
        abstract fun getColumn(): KProperty1<Habit, Int?>?
        abstract fun getColumnString(): KProperty1<Habit, String?>?
    }
}