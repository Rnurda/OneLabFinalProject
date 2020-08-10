package com.ryspay.onelabfinalproject.feature.unsplash.domain

import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.PhotoDomain

interface PhotosRepository {
    suspend fun getPhotos(): List<PhotoDomain>
}