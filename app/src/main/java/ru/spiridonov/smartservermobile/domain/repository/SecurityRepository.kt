package ru.spiridonov.smartservermobile.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.spiridonov.smartservermobile.domain.entity.Security

interface SecurityRepository {
    fun getLastSecurity(): Flow<Security?>

    suspend fun setSecurity(turnOn: Boolean)
}