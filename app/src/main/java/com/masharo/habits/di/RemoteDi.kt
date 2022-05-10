package com.masharo.habits.di

import com.masharo.habits.data.remote.HabitApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val remoteModule = module {
    factory { httpLoggingInterceptor() }
    factory { authorizationInterceptor() }
    factory { okHttpClient(get(), get()) }
    factory { retrofit(get()) }
    single { remoteApi(get()) }
}

private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return httpLoggingInterceptor
}

private fun authorizationInterceptor(): Interceptor = object: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authorizationResponse = chain
            .request()
            .newBuilder()
            .header("Authorization", "da57b0ce-6f54-40a6-94f4-9000b6a67152")
            .header("Content-Type", "application/json")
            .header("accept", "application/json")
            .build()
        return chain.proceed(authorizationResponse)
    }
}

private fun okHttpClient(logger: HttpLoggingInterceptor,
                         authorizationInterceptor: Interceptor
) = OkHttpClient
    .Builder()
    .addInterceptor(logger)
    .addInterceptor(authorizationInterceptor)
    .build()

private fun retrofit(okHttpClient: OkHttpClient) = Retrofit
    .Builder()
    .baseUrl("https://droid-test-server.doubletapp.ru/api/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private fun remoteApi(retrofit: Retrofit) = retrofit
    .create(HabitApi::class.java)