package com.masharo.habits.cleaner

import android.util.Log
import com.masharo.habits.dataNew.model.remote.IdRemote
import com.masharo.habits.dataNew.model.remote.HabitRemote
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP

fun main() {

    val instance = Instance()

    instance.api.getHabits().enqueue(object: Callback<List<HabitRemote>> {
            override fun onResponse(
                call: Call<List<HabitRemote>>,
                response: Response<List<HabitRemote>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.onEach {
                        instance.api.deleteHabit(IdRemote(id = it.questId)).enqueue(
                            object: Callback<Void> {
                                override fun onResponse(
                                    call: Call<Void>,
                                    response: Response<Void>
                                ) {
                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {
                                    t.message?.let {
                                        Log.i("clear", it)
                                    }
                                }
                            }
                        )
                    }
                }
            }

            override fun onFailure(call: Call<List<HabitRemote>>, t: Throwable) {
                t.message?.let {
                    Log.i("clear", it)
                }
            }
        })
    }



//
//    val test = Test()
////
//    CoroutineScope(Dispatchers.IO).launch {
//        test.api.addHabit(NewHabitRemote(
//        questColor = 0,
//        questCount = 0,
//        questDate = 0,
//        questDescription = "desc",
//        questDoneDates = arrayListOf(0, 1, 2, 3, 4),
//        questFrequency = 0,
//        questPriority = 1,
//        questTitle = "my test habit MEGA TEST",
//        questType = 1
//    ))
//    }
//
//    Thread.sleep(10000)

//    val res = result.execute()
//    println("MISS")
//    println(res.body())
//    println(res.code())
//    res.errorBody()?.apply {
//        val message = Gson().fromJson(string(), Message::class.java)
//        println(message)
//    }


class Instance {

    lateinit var api: LittleApi

    init {
        configureRetrofit()
    }

    private fun configureRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val authorizationInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
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

        api = retrofit.create(LittleApi::class.java)
    }
}

interface LittleApi {
    @GET("./habit")
    fun getHabits(): Call<List<HabitRemote>>

    @HTTP(method = "DELETE", path = "./habit", hasBody = true)
    fun deleteHabit(@Body params: IdRemote): Call<Void>
}