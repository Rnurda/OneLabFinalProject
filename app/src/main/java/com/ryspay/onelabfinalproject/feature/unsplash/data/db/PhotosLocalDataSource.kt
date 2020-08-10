package com.ryspay.onelabfinalproject.feature.unsplash.data.db

import com.ryspay.onelabfinalproject.feature.unsplash.data.db.dao.PhotosDao
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.entity.PhotoLocal

interface PhotosLocalDataSource {
    suspend fun savePhotos(photos: List<PhotoLocal>)
}

class RoomPhotosLocalDataSource(
    private val dao: PhotosDao
): PhotosLocalDataSource{
    override suspend fun savePhotos(photos: List<PhotoLocal>) {
        dao.savePhotos(photos)
    }
}