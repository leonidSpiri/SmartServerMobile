package ru.spiridonov.smartservermobile.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.spiridonov.smartservermobile.domain.entity.RaspState

interface RaspStateRepository {
    suspend fun refreshRaspStatesList()

    suspend fun getAllRaspStatesList(): Flow<List<RaspState>>
}