package com.masharo.habits.dataNew

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.masharo.habits.R
import com.masharo.habits.presentation.model.HabitPresentation
import kotlin.reflect.KProperty1

//Кароч у класса должено быть состояние, мы будем вызывать один метод который будет принимать лист
class HabitListFilter {

    var habitsOrigin: List<HabitPresentation> = listOf()
        set(value) {
            field = value
            habits.value = value
            update()
        }

    private var habits: MutableLiveData<List<HabitPresentation>> = MutableLiveData()

    private var filterColumn: Column = Column.TYPE
    private var filterValue: Int = 0

    fun getHabits(): LiveData<List<HabitPresentation>> = habits

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
        habits.value = sort(filter(habitsOrigin))
    }

    private fun sort(habitsLocal: List<HabitPresentation>): List<HabitPresentation> = habitsLocal.sortedBy {
            sortColumn.getColumn()?.invoke(it)
        }

    private fun filter(habitsLocal: List<HabitPresentation>): List<HabitPresentation> =
        if (search.isEmpty()) {
            habitsLocal
        } else {
            habitsLocal.filter {
                HabitPresentation::title.invoke(it).contains(search, true)
            }
        }

//    Не использую фильтрацию, но можно
//    private fun filter(habitsLocal: List<Habit>): List<Habit> =
//        if (search.isEmpty()) {
//            habitsLocal.filter {
//                filterColumn.getColumn()?.invoke(it) == filterValue
//            }
//        } else {
//            habitsLocal.filter {
//                filterColumn.getColumn()?.invoke(it) == filterValue &&
//                Habit::title.invoke(it).contains(search, true)
//            }
//        }

    
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
            override fun getColumn(): KProperty1<HabitPresentation, Int?> = HabitPresentation::id
            override fun getColumnString(): KProperty1<HabitPresentation, String?>? = null
            override fun toString(): String = "id"

        },
        TITLE {
            override fun getResource(): Int {
                return R.string.db_title
            }

            override fun useLike(): Boolean = true
            override fun useOrderBy(): Boolean = false
            override fun getColumn(): KProperty1<HabitPresentation, Int>? = null
            override fun getColumnString(): KProperty1<HabitPresentation, String?> = HabitPresentation::title
            override fun toString(): String = "Title"
        },
        DESCRIPTION {
            override fun getResource(): Int {
                return R.string.db_description
            }

            override fun useLike(): Boolean = false
            override fun useOrderBy(): Boolean = false
            override fun getColumn(): KProperty1<HabitPresentation, Int>? = null
            override fun getColumnString(): KProperty1<HabitPresentation, String?> = HabitPresentation::description
            override fun toString(): String = "Description"
        },
        TYPE {
            override fun getResource(): Int {
                return R.string.db_type
            }

            override fun useLike(): Boolean = false
            override fun useOrderBy(): Boolean = false
            override fun getColumn(): KProperty1<HabitPresentation, Int> = HabitPresentation::type
            override fun getColumnString(): KProperty1<HabitPresentation, String?>? = null
            override fun toString(): String = "Type"
        },
        COUNT {
            override fun getResource(): Int {
                return R.string.db_count
            }

            override fun useLike(): Boolean = false
            override fun useOrderBy(): Boolean = true
            override fun getColumn(): KProperty1<HabitPresentation, Int> = HabitPresentation::count
            override fun getColumnString(): KProperty1<HabitPresentation, String?>? = null
            override fun toString(): String = "Count"
        },
        PERIOD {
            override fun getResource(): Int {
                return R.string.db_period
            }

            override fun useLike(): Boolean = false
            override fun useOrderBy(): Boolean = true
            override fun getColumn(): KProperty1<HabitPresentation, Int> = HabitPresentation::period
            override fun getColumnString(): KProperty1<HabitPresentation, String?>? = null
            override fun toString(): String = "Period"
        },
        COUNT_DONE {
            override fun getResource(): Int {
                return R.string.db_count_done
            }

            override fun useLike(): Boolean = false
            override fun useOrderBy(): Boolean = true
            override fun getColumn(): KProperty1<HabitPresentation, Int> = HabitPresentation::countDone
            override fun getColumnString(): KProperty1<HabitPresentation, String?>? = null
            override fun toString(): String = "Count done"
        };

        abstract fun getResource(): Int
        abstract fun useLike(): Boolean
        abstract fun useOrderBy(): Boolean
        abstract fun getColumn(): KProperty1<HabitPresentation, Int?>?
        abstract fun getColumnString(): KProperty1<HabitPresentation, String?>?
    }
}