package com.ryspay.onelabfinalproject.feature.unsplash.data

import com.ryspay.onelabfinalproject.feature.unsplash.data.db.PhotosLocalDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.PhotosRemoteDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.domain.PhotosRepository
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.DetailPhotoDomain
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.PhotoDomain

class PhotosRepositoryImpl(
    private val remoteDataSource: PhotosRemoteDataSource,
    private val localDataSource: PhotosLocalDataSource
): PhotosRepository {

    override suspend fun getPhotos(
        page: Int,
        per_page: Int,
        order_by: String
    ): List<PhotoDomain> {
        val photoResponse = remoteDataSource.getPhotos(
            page = page,
            per_page = per_page,
            order_by = order_by
        )
//        localDataSource.savePhotos(photoResponse.map { it.toLocalEntity() })
        return photoResponse.map { it.toDomainModel() }
    }

    override suspend fun getPhoto(photo_id: String): DetailPhotoDomain {
        return remoteDataSource.getPhoto(photo_id).toDomainModel()
    }

    override suspend fun searchPhotos(
        query: String,
        page: Int,
        per_page: Int,
        order_by: String,
        color: String?
    ): List<PhotoDomain> {
        val hashMap = HashMap<String, String>()

        color?.let{
            hashMap["color"] = color
        }

        val searchResponse = remoteDataSource.searchPhotos(
            query = query,
            per_page = per_page,
            page = page,
            order_by = order_by,
            map = hashMap
        )

        return searchResponse.results.map {
            it.toDomainModel()
        }
    }


}