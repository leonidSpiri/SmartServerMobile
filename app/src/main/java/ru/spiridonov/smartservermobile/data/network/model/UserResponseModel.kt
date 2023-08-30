package ru.spiridonov.smartservermobile.data.network.model

import com.google.gson.annotations.Expose

data class UserResponseModel(
    @Expose
    val id: Long,
    @Expose
    val username: String,
    @Expose
    val email: String,
    @Expose
    val roles: List<String>,
    @Expose
    val accessToken: String,
    @Expose
    val refreshToken: String
)