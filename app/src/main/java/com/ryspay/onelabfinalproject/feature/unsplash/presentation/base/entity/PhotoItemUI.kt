package com.ryspay.onelabfinalproject.feature.unsplash.presentation.base.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoItemUI(
    val id: String,
    val raw_url: String,
    val createdAt: String,
    val width: Int,
    val height: Int
): Parcelable