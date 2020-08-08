package com.ryspay.onelabfinalproject.feature.unsplash.data

import com.ryspay.onelabfinalproject.feature.unsplash.data.db.PhotosLocalDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.PhotosRemoteDataSource

class PhotosRepositoryImpl(
    private val remoteDataSource: PhotosRemoteDataSource,
    private val localDataSource: PhotosLocalDataSource
) {

}