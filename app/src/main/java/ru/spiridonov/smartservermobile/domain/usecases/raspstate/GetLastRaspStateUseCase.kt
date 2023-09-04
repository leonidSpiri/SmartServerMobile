package ru.spiridonov.smartservermobile.domain.usecases.raspstate

import ru.spiridonov.smartservermobile.domain.repository.RaspStateRepository
import javax.inject.Inject

class GetLastRaspStateUseCase @Inject constructor(
    private val repository: RaspStateRepository
) {
    operator fun invoke() = repository.getLastRaspState()
}