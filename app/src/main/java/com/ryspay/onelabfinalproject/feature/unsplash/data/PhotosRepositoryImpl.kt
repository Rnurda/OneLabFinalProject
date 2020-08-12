package com.ryspay.onelabfinalproject.feature.unsplash.data

import android.content.Context
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.PhotosLocalDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.PhotosRemoteDataSource
import com.ryspay.onelabfinalproject.feature.unsplash.domain.PhotosRepository
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.DetailPhotoDomain
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.PhotoDomain
import com.ryspay.onelabfinalproject.utils.checkNetworkConnection

class PhotosRepositoryImpl(
    private val context: Context,
    private val remoteDataSource: PhotosRemoteDataSource,
    private val localDataSource: PhotosLocalDataSource
): PhotosRepository {

    override suspend fun getPhotos(): List<PhotoDomain> =
        localDataSource.getPhotos().map { it.toDomainModel() }

    override suspend fun fetchPhotos(page: Int, per_page: Int, order_by: String) {
        if (checkNetworkConnection(context)){
            val photoResponse = remoteDataSource.getPhotos(
                page = page,
                per_page = per_page,
                order_by = order_by
            )

            localDataSource.deleteAllPhotos()
            localDataSource.savePhotos(photoResponse.map { it.toLocalEntity() })
        }
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