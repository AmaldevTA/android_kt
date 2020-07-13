package com.aml.dagger.profile.repo

import javax.inject.Inject

class ProfileRepo @Inject constructor(
    private val localDataSource: ProfileLocalDataSource,
    private val remoteDataSource: ProfileRemoteDataSource
) {
}