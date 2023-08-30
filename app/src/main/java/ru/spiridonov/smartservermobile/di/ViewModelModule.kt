package ru.spiridonov.smartservermobile.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.spiridonov.smartservermobile.presentation.MainViewModel
import ru.spiridonov.smartservermobile.presentation.ui.dashboard.DashboardViewModel
import ru.spiridonov.smartservermobile.presentation.ui.home.HomeViewModel
import ru.spiridonov.smartservermobile.presentation.ui.notifications.NotificationsViewModel


@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    fun bindDashboardViewModel(viewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationsViewModel::class)
    fun bindNotificationsViewModel(viewModel: NotificationsViewModel): ViewModel

}