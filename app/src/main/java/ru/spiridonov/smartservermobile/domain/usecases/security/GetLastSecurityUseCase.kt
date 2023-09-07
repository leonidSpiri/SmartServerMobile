package ru.spiridonov.smartservermobile.domain.usecases.security

import ru.spiridonov.smartservermobile.domain.repository.SecurityRepository
import javax.inject.Inject

class GetLastSecurityUseCase @Inject constructor(
    private val repository: SecurityRepository
) {
    operator fun invoke() = repository.getLastSecurity()
}