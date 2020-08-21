package com.ryspay.onelabfinalproject.feature.unsplash.data

import com.ryspay.onelabfinalproject.feature.unsplash.data.db.entity.PhotoLocal
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto.DetailPhotoRemoteDTO
import com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto.PhotoRemoteDTO
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.DetailPhotoDomain
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.PhotoDomain

fun PhotoRemoteDTO.toDomainModel(): PhotoDomain {
    return PhotoDomain(
        id = id?:"-1",
        height = height?:-1,
        width = width?:-1,
        created_at = created_at?:"",
        raw_url = urls?.raw?:""
    )
}

fun DetailPhotoRemoteDTO.toDomainModel(): DetailPhotoDomain {
    return DetailPhotoDomain(
        id = id?:"-1",
        aperture = exif?.aperture?:"",
        description = description?:"",
        downloads = downloads?:-1,
        likes = likes?:-1,
        city = location?.city?:"",
        country = location?.country?:"",
        exposure_time = exif?.exposure_time?:"",
        focal_length = exif?.focal_length?:"",
        iso = exif?.iso?:-1,
        make = exif?.make?:"",
        model = exif?.model?:"",
        img_url = urls?.raw?:""
    )
}

fun PhotoRemoteDTO.toLocalEntity(): PhotoLocal{
    return PhotoLocal(
        id = id?:"-1",
        width = width?:-1,
        height = height?:-1,
        createdAt = created_at?:"",
        raw_url = urls?.raw?:""
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