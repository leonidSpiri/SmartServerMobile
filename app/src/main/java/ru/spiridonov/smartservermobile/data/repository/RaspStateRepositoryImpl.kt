package ru.spiridonov.smartservermobile.data.repository

import ru.spiridonov.smartservermobile.domain.entity.RaspState
import ru.spiridonov.smartservermobile.domain.repository.RaspStateRepository
import javax.inject.Inject

class RaspStateRepositoryImpl @Inject constructor(
) : RaspStateRepository {
    override suspend fun getRaspLastState(): RaspState {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRaspStatesList(): List<RaspState> {
        TODO("Not yet implemented")
    }
}