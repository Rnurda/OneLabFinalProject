package com.ryspay.onelabfinalproject.feature.unsplash.domain.usecases

import com.ryspay.onelabfinalproject.feature.unsplash.domain.PhotosRepository
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.PhotoDomain
import com.ryspay.onelabfinalproject.utils.Const

class FetchPhotosUseCase(
    private val repository: PhotosRepository
) {
    suspend operator fun invoke(
        page: Int = 1,
        per_page: Int = Const.DEFAULT_PER_PAGE,
        order_by: String = Const.ORDER_BY_LATEST
    ){
        repository.fetchPhotos(page, per_page, order_by)
    }
}