package ru.spiridonov.smartservermobile.data.repository

import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import ru.spiridonov.smartservermobile.data.database.user.UserDao
import ru.spiridonov.smartservermobile.data.mapper.DtoMapper
import ru.spiridonov.smartservermobile.data.mapper.UserMapper
import ru.spiridonov.smartservermobile.data.network.ApiService
import ru.spiridonov.smartservermobile.data.network.model.UserResponseModel
import ru.spiridonov.smartservermobile.data.storage.TokenStorage
import ru.spiridonov.smartservermobile.domain.entity.User
import ru.spiridonov.smartservermobile.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dtoMapper: DtoMapper,
    private val userMapper: UserMapper,
    private val tokenStorage: TokenStorage,
    private val userDao: UserDao
) : UserRepository {
    override suspend fun login(username: String, password: String): User? {
        try {
            val jsonObject = JSONObject()
            jsonObject.put("userName", username)
            jsonObject.put("password", password)
            val jsonObjectString = jsonObject.toString()
            val requestBody =
                jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            apiService.signIn(requestBody).body()?.let { userResponseModel ->
                return parseUserResponse(userResponseModel)
            }
            return null

        } catch (e: Exception) {
            Log.d("UserRepositoryImpl", e.toString())
            return null
        }
    }

    override suspend fun logout() {
        val user = userDao.getUser() ?: return
        val storedRefreshToken = user.refreshToken
        val jsonObject = JSONObject()
        jsonObject.put("refreshToken", storedRefreshToken)
        val jsonObjectString = jsonObject.toString()
        val requestBody =
            jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        apiService.logout(requestBody)
        tokenStorage.clearToken()
        userDao.deleteUser(user.id)
    }

    override suspend fun register(user: User): User? {
        try {
            val jsonObjectString = user.toString()
            val requestBody =
                jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            apiService.signUn(requestBody).body()?.let { userResponseModel ->
                return parseUserResponse(userResponseModel)
            }
            return null

        } catch (e: Exception) {
            Log.d("UserRepositoryImpl", e.toString())
            return null
        }
    }

    override suspend fun isUserLoggedIn() = userDao.getUser() != null

    override suspend fun getUser() = userDao.getUser()
        ?.let { userMapper.mapUserDbModelToUser(it) }

    override suspend fun getAccessToken(): String? {
        val storedToken = tokenStorage.getToken()
        if (storedToken == null) {
            val user = userDao.getUser() ?: return null
            val storedRefreshToken = user.refreshToken
            val jsonObject = JSONObject()
            jsonObject.put("refreshToken", storedRefreshToken)
            val jsonObjectString = jsonObject.toString()
            val requestBody =
                jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            apiService.refreshToken(requestBody).body()?.let { userResponseModel ->
                tokenStorage.setToken(userResponseModel.accessToken)
                userDao.addUser(user.copy(refreshToken = userResponseModel.refreshToken))
                return userResponseModel.accessToken
            }
        }
        return storedToken
    }

    private suspend fun parseUserResponse(userResponseModel: UserResponseModel): User {
        Log.d("UserRepositoryImpl", userResponseModel.toString())
        val user = dtoMapper.mapUserJsonContainerToUser(userResponseModel)
        val accessToken = userResponseModel.accessToken
        val refreshToken = userResponseModel.refreshToken
        tokenStorage.setToken(accessToken)
        userDao.addUser(userMapper.mapUserToUserDbModel(user, refreshToken))
        return user
    }
}