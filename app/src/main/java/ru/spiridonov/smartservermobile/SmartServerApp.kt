package ru.spiridonov.smartservermobile

import android.app.Application
import android.content.Context
import ru.spiridonov.smartservermobile.di.DaggerApplicationComponent

class SmartServerApp : Application()/*, Configuration.Provider*/ {

    //@Inject
    //lateinit var workerFactory: CurrWorkerFactory

    val component by lazy {
       DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
        appContext = applicationContext
    }

    /*override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(
                workerFactory
            )
            .build()
    }*/

    companion object {
        lateinit var appContext: Context
    }
}