package com.ryspay.onelabfinalproject.api

import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto.PhotoRemoteDTO
import retrofit2.http.GET

interface UnsplashPhotoApi {
    @GET("/photos")
    suspend fun getPhotos(): List<PhotoRemoteDTO>
}