package com.masharo.habits.presentation.listHabit

import androidx.lifecycle.*
import com.masharo.habits.dataNew.HabitListFilter
import com.masharo.habits.domain.usecase.GetAllHabitsUseCase
import com.masharo.habits.domain.usecase.LoadHabitsUseCase
import com.masharo.habits.presentation.domainToPresentationHabit
import com.masharo.habits.presentation.model.HabitPresentation
import kotlinx.coroutines.launch

class HabitListViewModel(
    loadHabitsUseCase: LoadHabitsUseCase,
    getAllHabitsUseCase: GetAllHabitsUseCase
): ViewModel() {

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