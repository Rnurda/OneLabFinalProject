package com.ryspay.onelabfinalproject.feature.unsplash.data

import com.ryspay.onelabfinalproject.feature.unsplash.data.db.entity.PhotoLocal
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto.DetailPhotoRemoteDTO
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto.PhotoRemoteDTO
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.DetailPhotoDomain
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.PhotoDomain

fun PhotoRemoteDTO.toDomainModel(): PhotoDomain {
    return PhotoDomain(
        id = id,
        height = height,
        width = width,
        created_at = created_at,
        raw_url = urls.raw
    )
}

fun DetailPhotoRemoteDTO.toDomainModel(): DetailPhotoDomain {
    return DetailPhotoDomain(
        id = id,
        aperture = exif.aperture,
        description = description,
        downloads = downloads,
        likes = likes,
        city = location.city,
        country = location.country,
        exposure_time = exif.exposure_time,
        focal_length = exif.focal_length,
        iso = exif.iso,
        make = exif.make,
        model = exif.model
    )
}

fun PhotoRemoteDTO.toLocalEntity(): PhotoLocal{
    return PhotoLocal(
        id = id,
        width = width,
        height = height,
        createdAt = created_at,
        raw_url = urls.raw
    )
}

fun PhotoLocal.toDomainModel(): PhotoDomain{
    return PhotoDomain(
        id = id,
        width = width,
        height = height,
        created_at = createdAt,
        raw_url = raw_url
    )
}