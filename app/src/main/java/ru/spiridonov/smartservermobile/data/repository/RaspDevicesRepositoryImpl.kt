package ru.spiridonov.smartservermobile.data.repository

import ru.spiridonov.smartservermobile.data.database.raspdev.RaspDevDao
import ru.spiridonov.smartservermobile.data.mapper.DtoMapper
import ru.spiridonov.smartservermobile.data.mapper.RaspDevMapper
import ru.spiridonov.smartservermobile.data.network.ApiService
import ru.spiridonov.smartservermobile.domain.entity.RaspDevices
import ru.spiridonov.smartservermobile.domain.repository.RaspDevicesRepository
import ru.spiridonov.smartservermobile.domain.usecases.user.GetAccessTokenUseCase
import javax.inject.Inject

class RaspDevicesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dtoMapper: DtoMapper,
    private val raspDevMapper: RaspDevMapper,
    private val raspDevDao: RaspDevDao,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : RaspDevicesRepository {
    override suspend fun getRaspDevices() =
        raspDevDao.getAll()?.map { raspDevMapper.mapRaspDevDbModelToRaspDev(it) }
            ?: updateRaspDevices()

    override suspend fun updateRaspDevices(): List<RaspDevices> {
        val token = getAccessTokenUseCase.invoke() ?: return emptyList()
        val raspDevList = mutableListOf<RaspDevices>()
        apiService.getRaspDev(token).body()?.let { raspDevResponseList ->
            if (raspDevResponseList.isNotEmpty()) raspDevDao.deleteAll()
            raspDevResponseList.forEach {
                val raspDev = dtoMapper.mapRaspDevJsonContainerToRaspDev(it)
                raspDevList.add(raspDev)
                raspDevDao.insert(raspDevMapper.mapRaspDevToRaspDevDbModel(raspDev))
            }
        }
        return raspDevList
    }

    override suspend fun getRaspDeviceByType(type: String) =
        raspDevDao.getByType(type)?.let { raspDevMapper.mapRaspDevDbModelToRaspDev(it) }

}