package ru.spiridonov.smartservermobile.data.repository

import ru.spiridonov.smartservermobile.domain.entity.User
import ru.spiridonov.smartservermobile.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
) : UserRepository {
    override suspend fun login(username: String, password: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override suspend fun register(user: User): User? {
        TODO("Not yet implemented")
    }

    override suspend fun isUserLoggedIn(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): User? {
        TODO("Not yet implemented")
    }

    override suspend fun getAccessToken(): String? {
        TODO("Not yet implemented")
    }

}