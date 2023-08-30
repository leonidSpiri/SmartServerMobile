package ru.spiridonov.smartservermobile.domain.entity

import ru.spiridonov.smartserver.model.enums.Roles

data class User(
    val id: Long? = null,

    val userName: String,

    val email: String,

    val password: String,

    val roles: Set<Roles>? = null
)
