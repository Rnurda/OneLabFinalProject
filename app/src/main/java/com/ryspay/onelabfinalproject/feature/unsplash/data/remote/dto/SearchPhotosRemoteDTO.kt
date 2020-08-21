package com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto

data class SearchPhotosRemoteDTO(
    val total: Int,
    val total_pages: Int,
    val results: List<PhotoRemoteDTO>
)