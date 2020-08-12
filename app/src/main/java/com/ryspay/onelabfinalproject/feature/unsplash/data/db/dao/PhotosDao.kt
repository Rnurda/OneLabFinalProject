package com.ryspay.onelabfinalproject.feature.unsplash.data.db.dao

import androidx.room.*
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.entity.PhotoLocal
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.entity.PhotoLocalTable
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePhotos(photos: List<PhotoLocal>)

    @Query("select * from $PhotoLocalTable")
    fun getPhotos(): List<PhotoLocal>

    @Query("delete from $PhotoLocalTable")
    fun deleteAllPhotos()
}