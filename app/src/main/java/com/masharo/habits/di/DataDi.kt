package com.masharo.habits.di

import com.masharo.habits.BASE_URL
import com.masharo.habits.TOKEN
import com.masharo.habits.data.repository.DBHabitRepositoryImpl
import com.masharo.habits.data.database.HabitDatabase
import com.masharo.habits.data.database.HabitStorage
import com.masharo.habits.data.database.HabitStorageImpl
import com.masharo.habits.data.remote.HabitApi
import com.masharo.habits.data.remote.HabitService
import com.masharo.habits.data.remote.HabitServiceImpl
import com.masharo.habits.data.repository.RemoteHabitRepositoryImpl
import com.masharo.habits.domain.repository.DBHabitRepository
import com.masharo.habits.domain.repository.RemoteHabitRepository
import com.masharo.habits.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    factory { httpLoggingInterceptor() }
    factory<Interceptor> { headersInterceptor() }
    factory { okHttpClient(get(), get()) }
    factory { retrofit(get()) }
    single { remoteApi(get()) }

    single { HabitDatabase.instance(get()) }

    factory<HabitService> {
        HabitServiceImpl(get())
    }

    factory<HabitStorage> {
        HabitStorageImpl(get())
    }

    factory<RemoteHabitRepository> { RemoteHabitRepositoryImpl(get()) }
    factory<DBHabitRepository> { DBHabitRepositoryImpl(get()) }

    factory<LoadHabitsUseCase> { LoadHabitsUseCase(get(), Dispatchers.IO) }
    factory<GetAllHabitsUseCase> { GetAllHabitsUseCase(get()) }
    factory<GetHabitUseCase> { GetHabitUseCase(get(), Dispatchers.IO) }
    factory<AddHabitUseCase> { AddHabitUseCase(get(), get(), Dispatchers.IO) }
    factory<EditHabitUseCase> { EditHabitUseCase(get(), get(), Dispatchers.IO) }
}

private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return httpLoggingInterceptor
}

private fun headersInterceptor() = object: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authorizationResponse = chain
            .request()
            .newBuilder()
            .header("Authorization", TOKEN)
            .header("Content-Type", "application/json")
            .header("accept", "application/json")
            .build()
        return chain.proceed(authorizationResponse)
    }
}

private fun okHttpClient(logger: HttpLoggingInterceptor,
                         headersInterceptor: Interceptor
) = OkHttpClient
    .Builder()
    .addInterceptor(logger)
    .addInterceptor(headersInterceptor)
    .build()

private fun retrofit(okHttpClient: OkHttpClient) = Retrofit
    .Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private fun remoteApi(retrofit: Retrofit) = retrofit
    .create(HabitApi::class.java)