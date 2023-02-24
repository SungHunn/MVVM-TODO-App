package com.example.todoapp

import android.app.Application
import com.example.todoapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        //TODO Koin Trigger
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Application)
            modules(appModule)
        }
    }
}