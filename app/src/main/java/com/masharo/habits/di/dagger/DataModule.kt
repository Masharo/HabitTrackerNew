package com.masharo.habits.di.dagger

import android.content.Context
import com.masharo.habits.data.database.HabitDatabase
import com.masharo.habits.data.database.HabitStorage
import com.masharo.habits.data.database.HabitStorageImpl
import com.masharo.habits.data.remote.HabitApi
import com.masharo.habits.data.remote.HabitRemote
import com.masharo.habits.data.remote.HabitService
import com.masharo.habits.data.remote.HabitServiceImpl
import com.masharo.habits.data.repository.DBHabitRepositoryImpl
import com.masharo.habits.data.repository.RemoteHabitRepositoryImpl
import com.masharo.habits.domain.repository.DBHabitRepository
import com.masharo.habits.domain.repository.RemoteHabitRepository
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideHabitDataBase(context: Context): HabitDatabase {
        return HabitDatabase.instance(context = context)
    }

    @Provides
    fun provideHabitStorage(db: HabitDatabase): HabitStorage {
        return HabitStorageImpl(db = db)
    }

    @Provides
    fun provideDBHabitRepository(storage: HabitStorage): DBHabitRepository {
        return DBHabitRepositoryImpl(storage = storage)
    }


    /////////////////////////////////////////////////////


    @Provides
    fun provideHabitApi(): HabitApi {
        return HabitRemote.getApi()
    }

    @Provides
    fun provideHabitService(context: Context): HabitService {
        return HabitServiceImpl(context = context)
    }

    @Provides
    fun provideRemoteHabitRepository(service: HabitService): RemoteHabitRepository {
        return RemoteHabitRepositoryImpl(service = service)
    }

}