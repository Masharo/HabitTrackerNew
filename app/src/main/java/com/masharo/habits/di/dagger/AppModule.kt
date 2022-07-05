package com.masharo.habits.di.dagger

import android.content.Context
import com.masharo.habits.domain.usecase.*
import com.masharo.habits.presentation.habit.HabitViewModelFactory
import com.masharo.habits.presentation.listHabit.HabitListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideHabitListViewModelFactory(
        loadHabitsUseCase: LoadHabitsUseCase,
        getAllHabitsUseCase: GetAllHabitsUseCase,
        incDoneCountHabitUseCase: IncDoneCountHabitUseCase
    ): HabitListViewModelFactory {
        return HabitListViewModelFactory(
            loadHabitsUseCase = loadHabitsUseCase,
            getAllHabitsUseCase = getAllHabitsUseCase,
            incDoneCountHabitUseCase = incDoneCountHabitUseCase
        )
    }

    @Provides
    fun provideHabitViewModelFactory(
        getHabitUseCase: GetHabitUseCase,
        addHabitUseCase: AddHabitUseCase,
        editHabitUseCase: EditHabitUseCase
    ): HabitViewModelFactory {
        return HabitViewModelFactory(
            getHabitUseCase = getHabitUseCase,
            addHabitUseCase = addHabitUseCase,
            editHabitUseCase = editHabitUseCase
        )
    }

//    @Singleton
//    @Provides
//    fun provideHabitListViewModelFactoryT(
//        loadHabitsUseCase: LoadHabitsUseCase,
//        getAllHabitsUseCase: GetAllHabitsUseCase,
//        context: Context
//    ): HabitListViewModel {
//        ViewModelProvider(context,
//        object : ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
//                HabitListViewModelFactory(
//                    loadHabitsUseCase = loadHabitsUseCase,
//                    getAllHabitsUseCase = getAllHabitsUseCase
//                ) as T
//        }
//        )
//
//
//    }
}