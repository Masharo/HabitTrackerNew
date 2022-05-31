package com.masharo.habits.presentation.listHabit

import androidx.lifecycle.*
import com.masharo.habits.R
import com.masharo.habits.domain.usecase.GetAllHabitsUseCase
import com.masharo.habits.domain.usecase.IncDoneCountHabitUseCase
import com.masharo.habits.domain.usecase.LoadHabitsUseCase
import com.masharo.habits.presentation.HabitListFilter
import com.masharo.habits.presentation.domainToPresentationHabit
import com.masharo.habits.presentation.model.HabitPresentation
import com.masharo.habits.presentation.presentationToDomainId
import kotlinx.coroutines.launch

class HabitListViewModel(
    loadHabitsUseCase: LoadHabitsUseCase,
    getAllHabitsUseCase: GetAllHabitsUseCase,
    val incDoneCountHabitUseCase: IncDoneCountHabitUseCase
): ViewModel() {

    private val localToastText: MutableLiveData<Pair<Int, Int>> = MutableLiveData()
    val toast: LiveData<Pair<Int, Int>> = localToastText

    private var habitListFilter: HabitListFilter = HabitListFilter()
    var habits: LiveData<List<HabitPresentation>> = getAllHabitsUseCase.execute()
        .asLiveData()
        .map { listHabit ->
            listHabit.map { habit ->
                domainToPresentationHabit(habit)
            }
        }

    init {
        viewModelScope.launch {
            loadHabitsUseCase.execute()
        }
    }

    fun incDoneCount(habit: HabitPresentation) {
        habit.id?.let {
            viewModelScope.launch {
                incDoneCountHabitUseCase.execute(presentationToDomainId(it))
            }

            localToastText.value = Pair(
                if (habit.getTypeEnum() == HabitPresentation.TypeHabit.POSITIVE) {
                    if (habit.countDone <= habit.count) {
                        R.string.good_min
                    } else {
                        R.string.good_max
                    }
                } else {
                    if (habit.countDone <= habit.count) {
                        R.string.bad_min
                    } else {
                        R.string.bad_max
                    }
                },
                habit.count - habit.countDone - 1
            )
        }
    }

    fun setFilterType(type: Int) {
        habitListFilter.setFilter(HabitListFilter.Column.TYPE, type)
    }

    fun setSort(column: HabitListFilter.Column) {
        habitListFilter.sortColumn = column
    }

    fun setSearch(search: String) {
        habitListFilter.search = search
    }

    fun getChangeHabits(): LiveData<List<HabitPresentation>> {
        return habitListFilter.getHabits()
    }

    fun setNewHabitList(habits: List<HabitPresentation>) {
        habitListFilter.habitsOrigin = habits
    }
}