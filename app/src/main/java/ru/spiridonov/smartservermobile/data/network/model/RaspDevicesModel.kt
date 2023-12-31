package ru.spiridonov.smartservermobile.data.network.model

import com.google.gson.annotations.Expose
import ru.spiridonov.smartservermobile.domain.entity.DevTypes

data class RaspDevicesModel(
    @Expose
    val id: Long,
    @Expose
    val devType: String,
    @Expose
    val pinId: Int,
    @Expose
    val description: String,
)
