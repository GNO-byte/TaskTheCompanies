package com.gno.taskthecompanies.retrofit

import com.gno.cryptmonitor.retrofit.RetrofitServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    const val BASE_URL = "https://lifehack.studio/test_task/"

    var retrofitService: RetrofitServices = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(RetrofitServices::class.java)

}