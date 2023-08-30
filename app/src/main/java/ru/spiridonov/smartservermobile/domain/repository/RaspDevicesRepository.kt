package ru.spiridonov.smartservermobile.domain.repository

import ru.spiridonov.smartservermobile.domain.entity.RaspDevices

interface RaspDevicesRepository {
    suspend fun getRaspDevices(): List<RaspDevices>

    suspend fun getRaspDeviceByType(type: String): RaspDevices?

    suspend fun updateRaspDevices(): RaspDevices
}