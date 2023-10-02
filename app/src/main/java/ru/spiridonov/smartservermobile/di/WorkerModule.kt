package ru.spiridonov.smartservermobile.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.spiridonov.smartservermobile.workers.ChildWorkerFactory
import ru.spiridonov.smartservermobile.workers.SecurityWorker

@Module
interface WorkerModule {
    @Binds
    @IntoMap
    @WorkerKey(SecurityWorker::class)
    fun bindSecurityWorkerFactory(worker: SecurityWorker.Factory): ChildWorkerFactory
}