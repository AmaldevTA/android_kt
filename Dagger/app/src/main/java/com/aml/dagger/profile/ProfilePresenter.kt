package com.aml.dagger.profile

import androidx.lifecycle.ViewModel
import com.aml.dagger.profile.repo.ProfileRepo
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val profileRepo: ProfileRepo
){

}