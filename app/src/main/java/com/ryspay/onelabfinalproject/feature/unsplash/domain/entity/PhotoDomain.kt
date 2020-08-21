package com.ryspay.onelabfinalproject.feature.unsplash.domain.entity

data class PhotoDomain(
    val id: String,
    val created_at: String,
    val height: Int,
    val width: Int,
    val raw_url: String
)