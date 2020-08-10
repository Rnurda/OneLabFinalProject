package com.ryspay.onelabfinalproject.feature.unsplash.data.remote

import com.ryspay.onelabfinalproject.api.UnsplashPhotoApi
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto.DetailPhotoRemoteDTO
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto.PhotoRemoteDTO
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto.SearchPhotosRemoteDTO
import com.ryspay.onelabfinalproject.utils.Const

interface PhotosRemoteDataSource {

    suspend fun getPhotos(
        page: Int,
        per_page: Int,
        order_by: String
    ): List<PhotoRemoteDTO>

    suspend fun getPhoto(photo_id: String): DetailPhotoRemoteDTO

    suspend fun searchPhotos(
        query: String,
        page: Int,
        per_page: Int,
        order_by: String,
        map: HashMap<String, String>
    ): SearchPhotosRemoteDTO
}

class RetrofitPhotosRemoteDataSource(
    private val api: UnsplashPhotoApi
): PhotosRemoteDataSource {

    override suspend fun getPhotos(
        page: Int,
        per_page: Int,
        order_by: String): List<PhotoRemoteDTO> {

        return api.getPhotos(page = page, per_page = per_page, order_by = order_by)
    }

    override suspend fun getPhoto(photo_id: String): DetailPhotoRemoteDTO {
        return api.getPhoto(photo_id = photo_id)
    }

    override suspend fun searchPhotos(
        query: String,
        page: Int,
        per_page: Int,
        order_by: String,
        map: HashMap<String, String>
    ): SearchPhotosRemoteDTO {
        return api.searchPhotos(
            query = query,
            page = page,
            per_page = per_page,
            order_by = order_by,
            map = map
        )
    }

}