package com.ryspay.onelabfinalproject.feature.unsplash.domain.usecases

import com.ryspay.onelabfinalproject.feature.unsplash.data.Listing
import com.ryspay.onelabfinalproject.feature.unsplash.domain.PhotosRepository
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI

class GetPagedPhotosUseCase(
    private val repository: PhotosRepository
) {
    operator fun invoke(): Listing<PhotoItemUI?>{
        return repository.getPagedPhotos()
    }
}