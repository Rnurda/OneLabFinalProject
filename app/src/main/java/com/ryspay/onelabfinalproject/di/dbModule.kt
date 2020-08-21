package com.ryspay.onelabfinalproject.di

import androidx.room.Room
import com.ryspay.onelabfinalproject.feature.unsplash.data.db.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val DB_NAME = "database-team2"

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java, DB_NAME
        )
            .build()
    }

    single {
        val database = get<AppDatabase>()
        return@single database.photosDao()
    }
}