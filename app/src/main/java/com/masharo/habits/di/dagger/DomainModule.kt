package com.masharo.habits.di.dagger

import com.masharo.habits.domain.repository.DBHabitRepository
import com.masharo.habits.domain.repository.RemoteHabitRepository
import com.masharo.habits.domain.usecase.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class DomainModule {

    @Provides
    fun provideLoadHabitsUseCase(
        remoteHabitRepository: RemoteHabitRepository
    ): LoadHabitsUseCase {
        return LoadHabitsUseCase(
            remoteRepository = remoteHabitRepository,
            Dispatchers.IO)
    }

    @Provides
    fun provideGetAllHabitsUseCase(
        dbRepository: DBHabitRepository
    ): GetAllHabitsUseCase {
        return GetAllHabitsUseCase(
            dbRepository = dbRepository
        )
    }

    @Provides
    fun provideGetHabitUseCase(
        dbRepository: DBHabitRepository
    ): GetHabitUseCase {
        return GetHabitUseCase(
            dbRepository = dbRepository,
            dispatcher = Dispatchers.IO
        )
    }

    @Provides
    fun provideAddHabitUseCase(
        dbRepository: DBHabitRepository,
        remoteRepository: RemoteHabitRepository
    ): AddHabitUseCase {
        return AddHabitUseCase(
            dbRepository = dbRepository,
            remoteRepository = remoteRepository,
            dispatcher = Dispatchers.IO
        )
    }

    @Provides
    fun provideEditHabitUseCase(
        dbRepository: DBHabitRepository,
        remoteRepository: RemoteHabitRepository
    ): EditHabitUseCase {
        return EditHabitUseCase(
            dbRepository = dbRepository,
            remoteRepository = remoteRepository,
            dispatcher = Dispatchers.IO
        )
    }

}