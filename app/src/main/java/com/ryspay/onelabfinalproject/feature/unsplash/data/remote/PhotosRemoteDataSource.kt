package com.ryspay.onelabfinalproject.feature.unsplash.data.remote

import com.ryspay.onelabfinalproject.api.UnsplashPhotoApi
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto.PhotoRemoteDTO

interface PhotosRemoteDataSource {
    suspend fun getPhotos(): List<PhotoRemoteDTO>
}

class RetrofitPhotosRemoteDataSource(
    private val api: UnsplashPhotoApi
): PhotosRemoteDataSource {

    override suspend fun getPhotos(): List<PhotoRemoteDTO> {
        return api.getPhotos()
    }

}