package ru.spiridonov.smartservermobile.domain.repository

import ru.spiridonov.smartservermobile.domain.entity.RaspState

interface RaspStateRepository {
    suspend fun getRaspLastState():RaspState

    suspend fun getAllRaspStatesList():List<RaspState>
}