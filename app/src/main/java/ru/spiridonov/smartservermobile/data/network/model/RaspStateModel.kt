package ru.spiridonov.smartservermobile.data.network.model

import com.google.gson.annotations.Expose

data class RaspStateModel(
    @Expose
    val id: Long,
    @Expose
    val dateTime: String,
    @Expose
    val raspState: String,
    @Expose
    val isSecurityViolated: Boolean = false
)
