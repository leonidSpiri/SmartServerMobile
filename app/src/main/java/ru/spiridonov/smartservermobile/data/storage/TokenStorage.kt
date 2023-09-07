package ru.spiridonov.smartservermobile.data.storage

import java.util.Date

object TokenStorage {

    private var accessToken: String? = null
    private var lastRefreshTime: Long = 0

    fun getToken(): String? {
        if ((lastRefreshTime + (9 * 60 * 1000)) < Date().time) {
            lastRefreshTime = 0
            accessToken = null
        }
        return accessToken
    }

    fun setToken(token: String) {
        accessToken = token
        lastRefreshTime = Date().time
    }

    fun clearToken() {
        accessToken = null
        lastRefreshTime = 0
    }
}