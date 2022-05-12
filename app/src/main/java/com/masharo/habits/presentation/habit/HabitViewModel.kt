package com.masharo.habits.presentation.habit

import android.content.Context
import android.view.View
import androidx.core.view.drawToBitmap
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.masharo.habits.data.HabitRepository
import com.masharo.habits.data.db.model.Habit
import com.masharo.habits.data.remote.model.PutResult
import com.masharo.habits.test.worker.AddHabitWorker
import com.masharo.habits.test.worker.HABIT_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HabitViewModel(
    private val context: Context,
    private val repository: HabitRepository,
    private val habitId: Int?
): ViewModel(), Observable {

//    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
//    private lateinit var fragmentManager: FragmentManager

//    private val repository = HabitRepositoryImpl(HabitDatabase.instance(context))
    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()
    private val habitLocal: MutableLiveData<Habit> = MutableLiveData(Habit())

    @Bindable
    val habit: LiveData<Habit> = habitLocal

    init {
        viewModelScope.launch(Dispatchers.IO) {
            habitId?.let { id ->
                repository.getHabit(id)?.let {
                    habitLocal.postValue(it)
                }
            }
        }
    }

    fun save() {

        habitLocal.value?.let { habit ->
            habit.id?.let {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.setHabit(habit)
                }
            } ?: run {
                viewModelScope.launch(Dispatchers.IO) {
                    workOperation<AddHabitWorker>(
                        repository.addHabit(habit).toInt()
                    )
                }
            }
        }

    }

    private inline fun <reified W : ListenableWorker>workOperation(id: Int) = WorkManager
        .getInstance(context)
        .enqueue(
            OneTimeWorkRequestBuilder<W>()
                .setInputData(
                    Data
                        .Builder()
                        .putInt(HABIT_ID, id)
                        .build()
                )
                .build()
        )


    fun doneInc() {
        habitLocal.value?.apply {
            countDone++
        }
    }

    fun doneDec() {
        habitLocal.value?.apply {
            if (countDone > 0) {
                countDone--
            }
        }
    }

    fun changeHabitColor(color: Int) {
        habit.value?.let {
            it.color = color
        }
    }

    fun colorCalculated(view: View, rootView: View) {
//      Нам все равно на высоту потому, что градиент горизонтальный
//      val y = (rootView.y / 2).toInt()

        val y = 0
        val x = view.background.bounds.centerX() + view.x.toInt()

        habit.value?.color = rootView.drawToBitmap().getColor(x, y).toArgb()

//        var arrF = FloatArray(3)
//        Color.colorToHSV(rootView.drawToBitmap().getColor(x, y).toArgb(), arrF)

//        rootView.drawToBitmap().getColor(x, y).getComponents(null).map { it * 255 }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }
}