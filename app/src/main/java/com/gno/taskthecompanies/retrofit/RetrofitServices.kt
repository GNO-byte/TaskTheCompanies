package com.gno.cryptmonitor.retrofit

import com.gno.taskthecompanies.retrofit.data.Company
import com.gno.taskthecompanies.retrofit.data.CompanyData
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {

    @GET("test.php")
    suspend fun getCompanies(
    ): ArrayList<Company>

    @GET("test.php")
    suspend fun getСompanyData(
        @Query("id") id: String
    ): ArrayList<CompanyData>

}