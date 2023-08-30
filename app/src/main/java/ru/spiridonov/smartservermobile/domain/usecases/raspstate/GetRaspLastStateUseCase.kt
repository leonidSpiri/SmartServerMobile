package ru.spiridonov.smartservermobile.domain.usecases.raspstate

import ru.spiridonov.smartservermobile.domain.repository.RaspStateRepository
import javax.inject.Inject

class GetRaspLastStateUseCase @Inject constructor(
    private val repository: RaspStateRepository
) {
    suspend operator fun invoke() = repository.getRaspLastState()
}