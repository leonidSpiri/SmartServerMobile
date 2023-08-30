package ru.spiridonov.smartservermobile.domain.usecases.user

import ru.spiridonov.smartservermobile.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() = repository.logout()
}