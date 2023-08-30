package ru.spiridonov.smartservermobile.domain.entity

data class RaspDevices(
    val id: Long? = null,

    val devType: DevTypes,

    val description: String,
)