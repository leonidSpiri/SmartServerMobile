package ru.spiridonov.smartservermobile.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.spiridonov.smartservermobile.data.database.raspdev.RaspDevAppDatabase
import ru.spiridonov.smartservermobile.data.database.raspdev.RaspDevDao
import ru.spiridonov.smartservermobile.data.database.user.UserAppDatabase
import ru.spiridonov.smartservermobile.data.database.user.UserDao
import ru.spiridonov.smartservermobile.data.network.ApiFactory
import ru.spiridonov.smartservermobile.data.network.ApiService
import ru.spiridonov.smartservermobile.data.repository.RaspDevicesRepositoryImpl
import ru.spiridonov.smartservermobile.data.repository.RaspStateRepositoryImpl
import ru.spiridonov.smartservermobile.data.repository.SecurityRepositoryImpl
import ru.spiridonov.smartservermobile.data.repository.UserRepositoryImpl
import ru.spiridonov.smartservermobile.data.storage.TokenStorage
import ru.spiridonov.smartservermobile.domain.repository.RaspDevicesRepository
import ru.spiridonov.smartservermobile.domain.repository.RaspStateRepository
import ru.spiridonov.smartservermobile.domain.repository.SecurityRepository
import ru.spiridonov.smartservermobile.domain.repository.UserRepository

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @ApplicationScope
    fun bindRaspDevicesRepository(impl: RaspDevicesRepositoryImpl): RaspDevicesRepository

    @Binds
    @ApplicationScope
    fun bindRaspStateRepository(impl: RaspStateRepositoryImpl): RaspStateRepository

    @Binds
    @ApplicationScope
    fun bindSecurityRepository(impl: SecurityRepositoryImpl): SecurityRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @Provides
        @ApplicationScope
        fun provideTokenStorage(): TokenStorage {
            return TokenStorage
        }

        @Provides
        @ApplicationScope
        fun provideUserDao(
            application: Application
        ): UserDao {
            return UserAppDatabase.getInstance(application).userDao()
        }

        @Provides
        @ApplicationScope
        fun provideRaspDevDao(
            application: Application
        ): RaspDevDao {
            return RaspDevAppDatabase.getInstance(application).raspDevDao()
        }
    }
}