package ru.spiridonov.smartservermobile.data.mapper

import ru.spiridonov.smartservermobile.data.network.model.UserResponseModel
import ru.spiridonov.smartservermobile.domain.entity.Roles
import ru.spiridonov.smartservermobile.domain.entity.User
import javax.inject.Inject

class DtoMapper @Inject constructor() {
    fun mapUserJsonContainerToUser(jsonContainer: UserResponseModel) =
        User(
            id = jsonContainer.id,
            userName = jsonContainer.username,
            email = jsonContainer.email,
            roles = jsonContainer.roles.toRolesSet()
        )
}
fun List<String>.toRolesSet(): Set<Roles> {
    val rolesSet = mutableSetOf<Roles>()
    this.forEach {
        when (it) {
            "ROLE_USER" -> rolesSet.add(Roles.ROLE_USER)
            "ROLE_ADMIN" -> rolesSet.add(Roles.ROLE_ADMIN)
        }
    }
    return rolesSet
}