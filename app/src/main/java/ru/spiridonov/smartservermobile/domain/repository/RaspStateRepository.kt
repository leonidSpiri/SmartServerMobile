package ru.spiridonov.smartservermobile.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.spiridonov.smartservermobile.domain.entity.RaspState

interface RaspStateRepository {
    suspend fun getRaspStateList()

    suspend fun getRequiredTemp(): Int

    suspend fun setNewRaspState(raspState: RaspState)

    fun getLastRaspState(): Flow<RaspState?>
}