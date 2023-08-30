package ru.spiridonov.smartservermobile.domain.entity

enum class Roles(roleValue: String, roleName: String) {
    ROLE_USER("USER", "Пользователь"),
    ROLE_ADMIN( "ADMIN", "Администратор" )
}