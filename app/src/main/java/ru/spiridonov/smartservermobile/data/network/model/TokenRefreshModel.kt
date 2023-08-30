package ru.spiridonov.smartservermobile.data.network.model

import com.google.gson.annotations.Expose

data class TokenRefreshModel(
    @Expose
    val accessToken: String,
    @Expose
    val refreshToken: String
)
