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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import ru.spiridonov.smartservermobile.data.mapper.DtoMapper
import ru.spiridonov.smartservermobile.data.network.ApiService
import ru.spiridonov.smartservermobile.domain.entity.Security
import ru.spiridonov.smartservermobile.domain.repository.SecurityRepository
import ru.spiridonov.smartservermobile.domain.usecases.user.GetAccessTokenUseCase
import java.util.Locale
import javax.inject.Inject

class SecurityRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dtoMapper: DtoMapper,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : SecurityRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private var securityState: Security? = null
    override fun getLastSecurity(): Flow<Security?> =
        flow {
            while (true) {
                downloadSecurityState()
                emit(securityState)
                delay(5900)
            }
        }.retry(10) {
            delay(10000)
            Log.d("SecurityRepositoryImpl", "getLastSecurity: ${it.message}")
            true
        }.catch {
            Log.d("SecurityRepositoryImpl", "getLastSecurity: ${it.message}")
        }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = securityState
        )

    override suspend fun setSecurity(turnOn: Boolean) {
        var token = getAccessTokenUseCase.invoke() ?: return
        token = "Bearer $token"
        val jsonObject = JSONObject()
        jsonObject.put("newRequiredState", turnOn.toString().lowercase(Locale.getDefault()))
        val jsonObjectString = jsonObject.toString()
        val requestBody =
            jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val response = apiService.setSecurity(token, requestBody)
        Log.d("setSecurity",response.code().toString() )
    }

    private suspend fun downloadSecurityState() {
        var token = getAccessTokenUseCase.invoke() ?: return
        token = "Bearer $token"
        apiService.getSecurity(token).body()?.let { securityStateModel ->
            securityState = dtoMapper.mapSecurityJsonContainerToSecurity(
                securityStateModel
            )
        }
    }
}