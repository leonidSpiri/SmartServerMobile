package ru.spiridonov.smartservermobile.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ru.spiridonov.smartservermobile.domain.entity.User
import java.time.OffsetDateTime

data class SecurityModel(
    @Expose
    val id: Long,
    @Expose
    val dateTime: String,
    @Expose
    val user: UserForSecurityResponseModel,
    @Expose
    val isSecurityTurnOn: Boolean
)

data class UserForSecurityResponseModel(
    @Expose
    val id: Long,
    @SerializedName("userName")
    @Expose
    val username: String
)
