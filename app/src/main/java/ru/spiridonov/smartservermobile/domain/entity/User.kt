package ru.spiridonov.smartservermobile.domain.entity

data class User(
    val id: Long,

    val userName: String,

    val email: String,

    val password: String,

    val roles: Set<Roles>
)
