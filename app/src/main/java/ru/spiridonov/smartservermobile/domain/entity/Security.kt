package ru.spiridonov.smartservermobile.domain.entity

import java.time.OffsetDateTime

data class Security(
    val id: Long,

    val dateTime: OffsetDateTime,

    val user: User,

    val isSecurityTurnOn: Boolean = true
)