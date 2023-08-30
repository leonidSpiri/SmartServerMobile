package ru.spiridonov.smartservermobile.domain.entity

import java.time.OffsetDateTime

data class RaspState(
    val id: Long? = null,

    val dateTime: OffsetDateTime,

    val raspState: List<Pair<RaspDevices, String>>,

    val isSecurityViolated: Boolean = false
)
