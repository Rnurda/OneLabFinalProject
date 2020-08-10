package com.ryspay.onelabfinalproject

import android.app.Application
import com.ryspay.onelabfinalproject.di.dbModule
import com.ryspay.onelabfinalproject.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                dbModule,
                mainModule
            )
        }
    }
}