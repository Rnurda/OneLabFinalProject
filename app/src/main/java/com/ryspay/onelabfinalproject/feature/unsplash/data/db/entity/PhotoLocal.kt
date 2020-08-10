package com.ryspay.onelabfinalproject.feature.unsplash.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val PhotoLocalTable = "local_photo"

@Entity(tableName = PhotoLocalTable)
data class PhotoLocal (
    @PrimaryKey
    val id: String,
    val updatedAt: String,
    val width: Int,
    val height: Int
)