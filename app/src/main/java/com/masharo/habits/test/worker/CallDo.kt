package com.masharo.habits.test.worker

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CallDo<T>: Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            //OK
        } else {

        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {

    }

}