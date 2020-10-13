package com.aml.dagger.profile.v2

import androidx.lifecycle.ViewModel
import com.aml.dagger.profile.repo.ProfileRepo
import javax.inject.Inject

class ProfileViewModel2 @Inject constructor(
    private val profileRepo: ProfileRepo
) : ViewModel() {

    fun testViewModel() {
        profileRepo.testRepo()
    }
}