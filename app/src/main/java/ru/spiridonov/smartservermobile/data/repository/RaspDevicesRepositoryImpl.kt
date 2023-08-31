package ru.spiridonov.smartservermobile.data.repository

import ru.spiridonov.smartservermobile.domain.entity.RaspDevices
import ru.spiridonov.smartservermobile.domain.repository.RaspDevicesRepository
import javax.inject.Inject

class RaspDevicesRepositoryImpl @Inject constructor(
) : RaspDevicesRepository {
    override suspend fun getRaspDevices(): List<RaspDevices> {
        TODO("Not yet implemented")
    }

    override suspend fun getRaspDeviceByType(type: String): RaspDevices? {
        TODO("Not yet implemented")
    }
}