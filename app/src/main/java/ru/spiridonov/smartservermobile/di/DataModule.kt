package ru.spiridonov.smartservermobile.di

import dagger.Binds
import dagger.Module
import ru.spiridonov.smartservermobile.data.repository.UserRepositoryImpl
import ru.spiridonov.smartservermobile.domain.repository.UserRepository

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository


    companion object {
        /*
                @Provides
                @ApplicationScope
                fun provideApiService(): ApiService {
                    return ApiFactory.apiService
                }

                @Provides
                @ApplicationScope
                fun provideMediaStorage(): MediaStorage {
                    return MediaStorage
                }
        */
    }
    /*     @Provides
         @ApplicationScope
         fun provideCurrListDao(
             application: Application
         ): CurrListDao {
             return AppDatabase.getInstance(application).currListDao()
         }
    */
}