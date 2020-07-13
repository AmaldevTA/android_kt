package com.aml.dagger.profile.repo

import com.aml.dagger.retrofit.RetrofitService
import javax.inject.Inject

class ProfileRemoteDataSource @Inject constructor(
    private val retrofitService: RetrofitService
){
}