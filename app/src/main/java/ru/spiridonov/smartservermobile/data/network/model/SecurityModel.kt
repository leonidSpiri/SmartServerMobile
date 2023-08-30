package ru.spiridonov.smartservermobile.data.network.model

import com.google.gson.annotations.Expose
import ru.spiridonov.smartservermobile.domain.entity.User
import java.time.OffsetDateTime

data class SecurityModel(
    @Expose
    val id: Long,
    @Expose
    val dateTime: OffsetDateTime,
    @Expose
    val user: User,
    @Expose
    val isSecurityTurnOn: Boolean
)
