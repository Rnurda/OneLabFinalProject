package com.ryspay.onelabfinalproject.feature.unsplash.domain.usecases

import com.ryspay.onelabfinalproject.feature.unsplash.data.Listing
import com.ryspay.onelabfinalproject.feature.unsplash.data.SearchListing
import com.ryspay.onelabfinalproject.feature.unsplash.domain.PhotosRepository
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI

class GetSearchPagedPhotosUseCase(
    private val repository: PhotosRepository
) {
    operator fun invoke(): SearchListing<PhotoItemUI?> {
        return repository.getSearchPagedPhotos()
    }
}