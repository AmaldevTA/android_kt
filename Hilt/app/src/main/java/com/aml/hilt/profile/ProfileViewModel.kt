package com.aml.hilt.profile


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.aml.hilt.profile.repo.ProfileRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val profileRepo: ProfileRepo
) : ViewModel(){

    fun printReferences(){
        profileRepo.printReferences()
    }
}