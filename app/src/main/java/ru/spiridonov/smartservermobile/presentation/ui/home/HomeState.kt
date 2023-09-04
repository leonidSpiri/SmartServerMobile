package ru.spiridonov.smartservermobile.presentation.ui.home

import ru.spiridonov.smartservermobile.domain.entity.RaspState

sealed class HomeState{
    data object Loading : HomeState()
    data class Content(val raspState: RaspState) : HomeState()
}
