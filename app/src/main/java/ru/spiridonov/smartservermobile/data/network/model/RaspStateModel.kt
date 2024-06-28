package ru.spiridonov.smartservermobile.data.network.model

import com.google.gson.annotations.Expose

data class RaspStateModel(
    @Expose
    val id: Long,
    @Expose
    val dateTime: String,
    @Expose
    val fanWorks: Boolean,
    @Expose
    val conditionerWorks: Boolean,
    @Expose
    val tempSensor: Int,
    @Expose
    val boxTempSensor: Int,
    @Expose
    val isSecurityViolated: Boolean = false
)
