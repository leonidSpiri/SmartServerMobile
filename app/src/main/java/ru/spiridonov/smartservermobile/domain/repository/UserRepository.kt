package ru.spiridonov.smartservermobile.domain.repository

import ru.spiridonov.smartservermobile.domain.entity.User

interface UserRepository {
    suspend fun login(username: String, password: String): User?
    suspend fun logout()
    suspend fun register(user:User): User?
    suspend fun isUserLoggedIn(): Boolean
    suspend fun getUser(): User?
    suspend fun getAccessToken(): String?
}