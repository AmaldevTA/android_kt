package com.aml.hilt.profile.repo

import javax.inject.Inject

class ProfileRepo @Inject constructor(
    private val localDataSource: ProfileLocalDataSource,
    private val remoteDataSource: ProfileRemoteDataSource
) {
    fun printReferences() {
        localDataSource.printReferences()
        remoteDataSource.printReferences()
    }
}