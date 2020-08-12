package com.ryspay.onelabfinalproject.feature.unsplash.domain.usecases

import com.ryspay.onelabfinalproject.feature.unsplash.domain.PhotosRepository
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.PhotoDomain

class GetPhotosUseCase(
    private val repository: PhotosRepository
) {
    suspend operator fun invoke(): List<PhotoDomain> {
        return repository.getPhotos()
    }
}