package com.ryspay.onelabfinalproject.feature.unsplash.domain.usecases

import com.ryspay.onelabfinalproject.feature.unsplash.data.Listing
import com.ryspay.onelabfinalproject.feature.unsplash.domain.PhotosRepository
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI

class GetPagedPhotosUseCase(
    private val repository: PhotosRepository
) {
    operator fun invoke(order_by: String): Listing<PhotoItemUI?>{
        return repository.getPagedPhotos(order_by)
    }
}