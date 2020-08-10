package com.ryspay.onelabfinalproject.feature.unsplash.data

import com.ryspay.onelabfinalproject.feature.unsplash.data.db.PhotosLocalDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.PhotosRemoteDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto.toDomainModel
import com.ryspay.onelabfinalproject.feature.unsplash.domain.PhotosRepository
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.PhotoDomain

class PhotosRepositoryImpl(
    private val remoteDataSource: PhotosRemoteDataSource,
    private val localDataSource: PhotosLocalDataSource
): PhotosRepository {
    override suspend fun getPhotos(): List<PhotoDomain> {
        return remoteDataSource.getPhotos().map {
            it.toDomainModel()
        }
    }

}