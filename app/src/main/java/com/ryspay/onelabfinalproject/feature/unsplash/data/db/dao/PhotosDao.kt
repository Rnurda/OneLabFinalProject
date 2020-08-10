package com.ryspay.onelabfinalproject.feature.unsplash.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.entity.PhotoLocal

@Dao
interface PhotosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePhotos(photos: List<PhotoLocal>)
}