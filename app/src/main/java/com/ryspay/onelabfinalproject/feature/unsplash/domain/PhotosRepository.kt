package com.ryspay.onelabfinalproject.feature.unsplash.domain

import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.DetailPhotoDomain
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.PhotoDomain

interface PhotosRepository {
    suspend fun getPhotos(): List<PhotoDomain>

    suspend fun fetchPhotos(
        page: Int,
        per_page: Int,
        order_by: String
    )

    suspend fun getPhoto(
        photo_id: String
    ): DetailPhotoDomain

    suspend fun searchPhotos(
        query: String,
        page: Int,
        per_page: Int,
        order_by: String,
        color: String?
    ): List<PhotoDomain>
}
