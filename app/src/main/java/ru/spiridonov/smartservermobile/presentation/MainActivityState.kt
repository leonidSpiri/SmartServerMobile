package ru.spiridonov.smartservermobile.presentation

sealed class MainActivityState {
    data object NeedToReLogin : MainActivityState()
    data object SetupView : MainActivityState()
}
