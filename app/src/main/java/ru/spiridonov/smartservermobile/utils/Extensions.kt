package ru.spiridonov.smartservermobile.utils

import ru.spiridonov.smartservermobile.domain.entity.Roles

object Extensions {
    fun Set<Roles>.toStringList(): List<String> {
        val rolesList = mutableListOf<String>()
        this.forEach {
            when (it) {
                Roles.ROLE_USER -> rolesList.add("ROLE_USER")
                Roles.ROLE_ADMIN -> rolesList.add("ROLE_ADMIN")
            }
        }
        return rolesList
    }
}