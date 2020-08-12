package com.ryspay.onelabfinalproject.feature.unsplash.presentation.base

import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.DetailPhotoDomain
import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.PhotoDomain
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.details.entity.DetailPhotoItemUI

fun PhotoDomain.toPresentationModel(): PhotoItemUI{
    return PhotoItemUI(
        id = id,
        raw_url = raw_url,
        createdAt = created_at,
        width = width,
        height = height
    )
}

fun DetailPhotoDomain.toPresentationModel(): DetailPhotoItemUI{
    return DetailPhotoItemUI(
        id = id,
        description = description,
        downloads = downloads,
        likes = likes,
        city = city,
        country = country,
        aperture = aperture,
        exposure_time = exposure_time,
        focal_length = focal_length,
        iso = iso,
        make = make,
        model = model
    )
}