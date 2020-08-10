package com.ryspay.onelabfinalproject.feature.unsplash.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.dao.PhotosDao
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.entity.PhotoLocal

@Database(
    entities = [PhotoLocal::class],
    version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photosDao() : PhotosDao
}
