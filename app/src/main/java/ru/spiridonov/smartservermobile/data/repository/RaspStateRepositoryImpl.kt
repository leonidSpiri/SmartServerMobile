package ru.spiridonov.smartservermobile.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import ru.spiridonov.smartservermobile.data.mapper.DtoMapper
import ru.spiridonov.smartservermobile.data.network.ApiService
import ru.spiridonov.smartservermobile.domain.entity.RaspState
import ru.spiridonov.smartservermobile.domain.repository.RaspStateRepository
import ru.spiridonov.smartservermobile.domain.usecases.raspdevices.GetRaspDeviceByTypeUseCase
import ru.spiridonov.smartservermobile.domain.usecases.user.GetAccessTokenUseCase
import javax.inject.Inject

class RaspStateRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val dtoMapper: DtoMapper,
    private val getRaspDeviceByTypeUseCase: GetRaspDeviceByTypeUseCase
) : RaspStateRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private var raspState: RaspState? = null

    override suspend fun getRaspStateList() {
    }

    override suspend fun getRequiredTemp(): Int {
        var token = getAccessTokenUseCase.invoke() ?: return 0
        token = "Bearer $token"
        return apiService.getRequiredTemp(token)
    }

    override fun getLastRaspState(): Flow<RaspState?> = flow {
        while (true) {
            downloadRaspState()
            emit(raspState)
            delay(20000)
        }
    }.retry(10) {
        delay(10000)
        Log.d("RaspStateRepositoryImpl", "getLastRaspState: ${it.message}")
        true
    }.catch {
        Log.d("RaspStateRepositoryImpl", "getLastRaspState: ${it.message}")
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = raspState
    )

    private suspend fun downloadRaspState() {
        var token = getAccessTokenUseCase.invoke() ?: return
        token = "Bearer $token"
        apiService.lastRaspState(token).body()?.let { raspStateResponseList ->
            raspState = dtoMapper.mapRaspStateJsonContainerToRaspState(
                raspStateResponseList,
                getRaspDeviceByTypeUseCase
            )
        }

    }
}