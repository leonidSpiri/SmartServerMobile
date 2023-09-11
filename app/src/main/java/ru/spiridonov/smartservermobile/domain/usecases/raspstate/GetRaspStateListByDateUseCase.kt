package ru.spiridonov.smartservermobile.domain.usecases.raspstate

import ru.spiridonov.smartservermobile.domain.repository.RaspStateRepository
import javax.inject.Inject

class GetRaspStateListByDateUseCase @Inject constructor(
    private val repository: RaspStateRepository
) {
    suspend operator fun invoke(date: String) = repository.getRaspStateListByDate(date)
}