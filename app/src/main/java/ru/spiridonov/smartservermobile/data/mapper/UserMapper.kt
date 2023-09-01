package ru.spiridonov.smartservermobile.data.mapper

import ru.spiridonov.smartservermobile.data.database.user.UserDbModel
import ru.spiridonov.smartservermobile.domain.entity.Roles
import ru.spiridonov.smartservermobile.domain.entity.User
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun mapUserToUserDbModel(user: User, refreshToken: String) =
        UserDbModel(
            id = user.id,
            username = user.userName,
            email = user.email,
            roles = user.roles.toStringList(),
            refreshToken = refreshToken
        )

    fun mapUserDbModelToUser(userDbModel: UserDbModel) =
        User(
            id = userDbModel.id,
            userName = userDbModel.username,
            email = userDbModel.email,
            roles = userDbModel.roles.toRolesSet()
        )
}

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