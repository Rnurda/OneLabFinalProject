package com.ryspay.onelabfinalproject.feature.unsplash.presentation.details.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailPhotoItemUI(
    val id: String,
    val description: String,
    val downloads: Int,
    val likes: Int,
    val city: String,
    val country: String,
    val aperture: String,
    val exposure_time: String,
    val focal_length: String,
    val iso: Int,
    val make: String,
    val model: String
): Parcelable