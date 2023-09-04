package ru.spiridonov.smartservermobile.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.spiridonov.smartservermobile.domain.entity.RaspState

interface RaspStateRepository {
    suspend fun getRaspStateList()

    fun getLastRaspState(): Flow<RaspState?>
}