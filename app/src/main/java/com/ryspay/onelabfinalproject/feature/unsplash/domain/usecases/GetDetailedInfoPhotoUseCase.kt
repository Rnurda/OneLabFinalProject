package com.ryspay.onelabfinalproject.feature.unsplash.domain.usecases

import com.ryspay.onelabfinalproject.feature.unsplash.domain.PhotosRepository
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.DetailPhotoDomain

class GetDetailedInfoPhotoUseCase(
    private val repository: PhotosRepository
) {
    suspend operator fun invoke(id :String): DetailPhotoDomain{
        return repository.getPhoto(id)
    }
}