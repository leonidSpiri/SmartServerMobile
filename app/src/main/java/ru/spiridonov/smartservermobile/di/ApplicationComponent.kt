package ru.spiridonov.smartservermobile.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.spiridonov.smartservermobile.SmartServerApp
import ru.spiridonov.smartservermobile.presentation.MainActivity
import ru.spiridonov.smartservermobile.presentation.auth.AuthActivity
import ru.spiridonov.smartservermobile.presentation.ui.dashboard.DashboardFragment
import ru.spiridonov.smartservermobile.presentation.ui.home.HomeFragment
import ru.spiridonov.smartservermobile.presentation.ui.notifications.NotificationsFragment

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
        WorkerModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: SmartServerApp)

    fun inject(activity: MainActivity)

    fun inject(activity: AuthActivity)

    fun inject(fragment: DashboardFragment)

    fun inject(fragment: HomeFragment)

    fun inject(fragment: NotificationsFragment)


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}