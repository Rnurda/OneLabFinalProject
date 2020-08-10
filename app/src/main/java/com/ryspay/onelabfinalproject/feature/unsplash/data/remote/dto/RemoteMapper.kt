package com.ryspay.onelabfinalproject.feature.unsplash.data.remote.dto

import com.ryspay.onelabfinalproject.feature.unsplash.domain.entity.PhotoDomain

fun PhotoRemoteDTO.toDomainModel(): PhotoDomain{
    return PhotoDomain(
        id = this.id,
        height = this.height,
        width = this.width,
        created_at = this.created_at
    )
}