package com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto

data class DetailPhotoRemoteDTO(
    val color: String,
    val created_at: String,
    val current_user_collections: List<CurrentUserCollection>,
    val description: String,
    val downloads: Int,
    val exif: Exif,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val location: Location,
    val tags: List<Tag>,
    val updated_at: String,
    val width: Int,
    val urls: Urls,
    val links: Links,
    val user: User
)

data class CurrentUserCollection(
    val cover_photo: Any,
    val id: Int,
    val last_collected_at: String,
    val published_at: String,
    val title: String,
    val updated_at: String,
    val user: Any
)

data class Exif(
    val aperture: String,
    val exposure_time: String,
    val focal_length: String,
    val iso: Int,
    val make: String,
    val model: String
)

data class Location(
    val city: String,
    val country: String,
    val position: Position
)

data class Tag(
    val title: String
)

data class Position(
    val latitude: Double,
    val longitude: Double
)