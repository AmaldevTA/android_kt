package com.aml.hilt.profile.repo

import com.aml.hilt.retrofit.RetrofitService
import javax.inject.Inject

class ProfileRemoteDataSource @Inject constructor(
    private val retrofitService: RetrofitService
) {

    fun printReferences() {
        println("<<<>>>  $retrofitService")
    }
}