package ru.spiridonov.smartservermobile.domain.usecases.raspdevices

import ru.spiridonov.smartservermobile.domain.repository.RaspDevicesRepository
import javax.inject.Inject

class GetRaspDeviceByTypeUseCase @Inject constructor(
    private val repository: RaspDevicesRepository
) {
    suspend operator fun invoke(type: String) = repository.getRaspDeviceByType(type)
}