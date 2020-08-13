package com.ryspay.onelabfinalproject.feature.unsplash.domain

import com.ryspay.onelabfinalproject.feature.unsplash.data.Listing
import com.ryspay.onelabfinalproject.feature.unsplash.data.paging.PhotosPageKeyedDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.DetailPhotoDomain
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.PhotoDomain
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI

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

    fun getPhotosFactory(
        order_by: String
    ): PhotosPageKeyedDataSource.Factory

    fun getPagedPhotos(
        order_by: String
    ): Listing<PhotoItemUI?>
}
