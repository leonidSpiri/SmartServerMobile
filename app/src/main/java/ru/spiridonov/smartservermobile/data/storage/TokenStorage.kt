package ru.spiridonov.smartservermobile.data.storage

object TokenStorage {

    private var accessToken: String? = null

    //TODO last update time
    fun getToken() = accessToken

    fun setToken(token: String) {
        accessToken = token
    }
}