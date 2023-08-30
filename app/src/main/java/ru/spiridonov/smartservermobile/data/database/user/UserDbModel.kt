package ru.spiridonov.smartservermobile.data.database.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDbModel(
    @PrimaryKey
    val id: Long,
    val username: String,
    val email: String,
    val roles: List<String>,
    val refreshToken: String
)
