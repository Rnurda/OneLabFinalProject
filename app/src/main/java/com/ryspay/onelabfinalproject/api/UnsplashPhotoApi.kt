package com.ryspay.onelabfinalproject.api

import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto.DetailPhotoRemoteDTO
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto.PhotoRemoteDTO
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto.SearchPhotosRemoteDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface UnsplashPhotoApi {
    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("order_by") order_by: String
    ): List<PhotoRemoteDTO>

    @GET("photos/{photo_id}")
    suspend fun getPhoto(
        @Path("photo_id") photo_id: String
    ): DetailPhotoRemoteDTO

    @GET("/search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("order_by") order_by: String,
        @QueryMap map: HashMap<String, String>
    ): SearchPhotosRemoteDTO
}