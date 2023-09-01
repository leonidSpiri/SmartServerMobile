package ru.spiridonov.smartservermobile.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.spiridonov.smartservermobile.data.network.ApiService
import ru.spiridonov.smartservermobile.domain.entity.RaspState
import ru.spiridonov.smartservermobile.domain.repository.RaspStateRepository
import ru.spiridonov.smartservermobile.domain.usecases.user.GetAccessTokenUseCase
import javax.inject.Inject

class RaspStateRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : RaspStateRepository {

    private val refreshEvents = MutableSharedFlow<Unit>()
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override suspend fun refreshRaspStatesList() {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRaspStatesList(): Flow<List<RaspState>> {
        TODO("Not yet implemented")
    }

    private suspend fun downloadRaspState(){
        val token = getAccessTokenUseCase.invoke() ?: return
        apiService.lastRaspState(token).body()?.let { raspStateResponseList ->

        }
    }
}