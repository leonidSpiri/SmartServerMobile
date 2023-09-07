package ru.spiridonov.smartservermobile.domain.usecases.raspstate

import ru.spiridonov.smartservermobile.domain.entity.RaspState
import ru.spiridonov.smartservermobile.domain.repository.RaspStateRepository
import javax.inject.Inject

class SetNewRaspStateUseCase @Inject constructor(
    private val repository: RaspStateRepository
) {
    suspend operator fun invoke(raspState: RaspState) = repository.setNewRaspState(raspState)
}