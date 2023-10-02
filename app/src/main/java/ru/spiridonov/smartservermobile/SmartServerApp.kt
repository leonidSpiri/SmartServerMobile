package ru.spiridonov.smartservermobile

import android.app.Application
import androidx.work.Configuration
import ru.spiridonov.smartservermobile.di.DaggerApplicationComponent
import ru.spiridonov.smartservermobile.workers.SecurityWorkerFactory
import javax.inject.Inject

class SmartServerApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: SecurityWorkerFactory

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(
                workerFactory
            )
            .build()
    }
}