package com.ryspay.onelabfinalproject.feature.unsplash.data.db

import androidx.paging.ItemKeyedDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.dao.PhotosDao
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.entity.PhotoLocal
import kotlinx.coroutines.flow.Flow

interface PhotosLocalDataSource {
    suspend fun savePhotos(photos: List<PhotoLocal>)
    suspend fun getPhotos(): List<PhotoLocal>
    suspend fun deleteAllPhotos()
}

class RoomPhotosLocalDataSource(
    private val dao: PhotosDao
): PhotosLocalDataSource{
    override suspend fun savePhotos(photos: List<PhotoLocal>) {
        dao.savePhotos(photos)
    }

    override suspend fun getPhotos(): List<PhotoLocal> = dao.getPhotos()

    override suspend fun deleteAllPhotos() = dao.deleteAllPhotos()
}

