package com.masharo.habits.data.remote

import com.masharo.habits.data.BASE_URL
import com.masharo.habits.data.TOKEN
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HabitRemote {

    @Volatile
    private var remote_local: HabitApi? = null
    private val remote: HabitApi by lazy(LazyThreadSafetyMode.NONE) {
        remote_local ?: throw Exception("Ошибка инициализации удаленного подключения")
    }

    fun getApi(): HabitApi {
        remote_local ?: run {
            @Synchronized
            if (remote_local == null) {
                remote_local = remoteApi(
                    retrofit = retrofit(
                        okHttpClient = okHttpClient(
                            logger = httpLoggingInterceptor(),
                            headers = headersInterceptor()
                        )
                    )
                )
            }
        }

        return remote
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
                             headers: Interceptor
    ) = OkHttpClient
        .Builder()
        .addInterceptor(logger)
        .addInterceptor(headers)
        .build()

    private fun retrofit(okHttpClient: OkHttpClient) = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun remoteApi(retrofit: Retrofit) = retrofit
        .create(HabitApi::class.java)

}