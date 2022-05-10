package com.masharo.habits.test.tested

import android.util.Log
import com.google.gson.Gson
import com.masharo.habits.data.remote.model.HabitRemote
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.Exception
import java.lang.reflect.Type

class TestFactory: CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? = when(returnType) {
        Array<HabitRemote>::class.java -> TestAdapter()
        else -> null
    }

    class TestAdapter: CallAdapter<String, Array<HabitRemote>?> {
        override fun responseType(): Type {
            return Array<HabitRemote>::class.java
        }

        override fun adapt(call: Call<String>): Array<HabitRemote>? {
            try {
                return Gson().fromJson(call.execute().body(), Array<HabitRemote>::class.java)
            } catch (ex: Exception) {
                Log.i("myLog", ex.toString())
            }

            return null
        }
    }

}