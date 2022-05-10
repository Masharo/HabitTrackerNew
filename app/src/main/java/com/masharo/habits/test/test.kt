package com.masharo.habits.test

import com.google.gson.Gson
import com.masharo.habits.data.remote.HabitApi
import com.masharo.habits.data.remote.model.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun main() {
//    val test = Test()
//
//    val result = test.api.addHabit(NewHabitRemote(
//        questColor = 0,
//        questCount = 0,
//        questDate = 0,
//        questDescription = "desc",
//        questDoneDates = arrayListOf(0, 1, 2, 3, 4),
//        questFrequency = 0,
//        questPriority = 1,
//        questTitle = "my test habit",
//        questType = 1
//    ))
//
//    val res = result.execute().body()
//    println(res)
//
    val test = Test()

    val result = test.api.putHabit(HabitRemote(
        questColor = 0,
        questCount = 0,
        questDate = 2,
        questDescription = "desc",
        questDoneDates = arrayListOf(0, 1, 2, 3, 4),
        questFrequency = 0,
        questPriority = 1,
        questTitle = "my test habit123",
        questType = 1,
        questId = "133ebd98-eb23-4de9-8c61-48aced1295f8"
        )
    )

    val res = result.execute()
    println(res.body())
    println(res.code())
    res.errorBody()?.apply {
        val message = Gson().fromJson(string(), Message::class.java)
        println(message)
    }



//    val test = Test()
//
//    val result = test.api.doneHabit(DoneParams(4, "133ebd98-eb23-4de9-8c61-48aced1295f8"))
//
//    val res = result.execute()
//    println(res.body())
//    println(res.code())
//    res.errorBody()?.apply {
//        val message = Gson().fromJson(string(), Message::class.java)
//        println(message)
//    }

//    val test = Test()
//
//    val result = test.api.deleteHabit(DeleteParams(
//        id = "133ebd98-eb23-4de9-8c61-48aced1295f8"
//    ))
//
//    val res = result.execute()
//    println(res.body())
//    println(res.code())
//    res.errorBody()?.apply {
//        val message = Gson().fromJson(string(), Message::class.java)
//        println(message)
//    }
}

class Test {

    lateinit var api: HabitApi

    init {
        configureRetrofit()
    }

    private fun configureRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val authorizationInterceptor = object : Interceptor {
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

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authorizationInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://droid-test-server.doubletapp.ru/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(HabitApi::class.java)
    }
}