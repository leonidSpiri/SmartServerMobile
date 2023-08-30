package ru.spiridonov.smartservermobile.data.network.model

import com.google.gson.annotations.Expose
import ru.spiridonov.smartservermobile.domain.entity.RaspDevices
import java.time.OffsetDateTime

data class RaspStateModel(
    @Expose
    val id: Long,
    @Expose
    val dateTime: OffsetDateTime,
    @Expose
    val raspState: List<Pair<RaspDevices, String>>,
    @Expose
    val isSecurityViolated: Boolean = false
)
