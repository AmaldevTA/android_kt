package com.aml.dagger.profile.v2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aml.dagger.profile.repo.ProfileRepo
import javax.inject.Inject

class ProfileViewModel1(
    private val profileRepo: ProfileRepo
) : ViewModel() {

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val profileRepo: ProfileRepo) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel1(profileRepo) as T
        }
    }

    fun testViewModel() {
        profileRepo.testRepo()
    }
}