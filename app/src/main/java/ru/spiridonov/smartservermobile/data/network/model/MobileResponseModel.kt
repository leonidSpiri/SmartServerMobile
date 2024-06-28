package ru.spiridonov.smartservermobile.data.network.model

data class MobileResponseModel(
    val newRequiredState: NewRequiredState
)

data class NewRequiredState(
    val fan: String,
    val conditioner: String,
    val tempSensor: Int
)