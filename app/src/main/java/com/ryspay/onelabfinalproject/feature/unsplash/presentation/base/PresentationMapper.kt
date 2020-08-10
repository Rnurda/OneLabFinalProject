package com.ryspay.onelabfinalproject.feature.unsplash.presentation.base

import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.PhotoDomain
import com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity.PhotoItemUI

fun PhotoDomain.toPresentationModel(): PhotoItemUI{
    return PhotoItemUI(
        id = id,
        raw_url = raw_url,
        createdAt = created_at,
        width = width,
        height = height
    )
}