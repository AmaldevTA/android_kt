package com.aml.dagger.profile.v1

import androidx.lifecycle.ViewModel
import com.aml.dagger.profile.repo.ProfileRepo
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val profileRepo: ProfileRepo
){
    fun testViewModel() {
        profileRepo.testRepo()
    }
}