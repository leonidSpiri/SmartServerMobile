package ru.spiridonov.smartservermobile.data.network

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.spiridonov.smartservermobile.data.network.model.RaspDevicesModel
import ru.spiridonov.smartservermobile.data.network.model.RaspStateModel
import ru.spiridonov.smartservermobile.data.network.model.SecurityModel
import ru.spiridonov.smartservermobile.data.network.model.TokenRefreshModel
import ru.spiridonov.smartservermobile.data.network.model.UserResponseModel

interface ApiService {

    @GET("rasp_state/last_response")
    suspend fun lastRaspState(
        @Header("Authorization") token: String
    ): Response<RaspStateModel>

    @GET("rasp_state/all_responses")
    suspend fun allRaspState(
        @Header("Authorization") token: String
    ): Response<List<RaspStateModel>>

    @GET("rasp_dev")
    suspend fun getRaspDev(
        @Header("Authorization") token: String
    ): Response<List<RaspDevicesModel>>

    @POST("security")
    suspend fun setSecurity(
        @Header("Authorization") token: String,
        @Body requestBody: RequestBody
    ): Response<SecurityModel>

    @GET("security/get")
    suspend fun getSecurity(
        @Header("Authorization") token: String
    ): Response<SecurityModel>

    @POST("mobile")
    suspend fun newMobileRequest(
        @Header("Authorization") token: String,
        @Body requestBody: RequestBody
    ): Response<*>

    @GET("mobile/required_temp")
    suspend fun getRequiredTemp(
        @Header("Authorization") token: String
    ): Int

    @POST("auth/signin")
    suspend fun signIn(
        @Body requestBody: RequestBody
    ): Response<UserResponseModel>

    @POST("auth/signup")
    suspend fun signUn(
        @Body requestBody: RequestBody
    ): Response<UserResponseModel>

    @POST("auth/refreshtoken")
    suspend fun refreshToken(
        @Body requestBody: RequestBody
    ): Response<TokenRefreshModel>

    @POST("auth/logout")
    suspend fun logout(
        @Body requestBody: RequestBody
    ): Response<String>
}