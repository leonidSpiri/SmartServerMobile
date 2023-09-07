package ru.spiridonov.smartservermobile.domain.usecases.security

import ru.spiridonov.smartservermobile.domain.repository.SecurityRepository
import javax.inject.Inject

class SetSecurityUseCase @Inject constructor(
    private val repository: SecurityRepository
) {
    suspend operator fun invoke(turnOn: Boolean) = repository.setSecurity(turnOn)
}