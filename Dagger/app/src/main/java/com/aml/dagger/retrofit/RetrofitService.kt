package com.aml.dagger.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface RetrofitService {

    @GET("payment/{id}/profile")
    fun getProfile(@Path("id") id: Int, @Header("Country-Code") code: String): Call<String?>?
}