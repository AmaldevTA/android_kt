package com.aml.hilt.profile

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.aml.hilt.profile.repo.ProfileRepo
import dagger.hilt.android.scopes.FragmentScoped

@FragmentScoped
class ProfileViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val profileRepo: ProfileRepo
) : ViewModel(){
}