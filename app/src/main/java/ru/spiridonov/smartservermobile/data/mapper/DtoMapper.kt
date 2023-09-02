package ru.spiridonov.smartservermobile.data.mapper

import android.util.Log
import ru.spiridonov.smartservermobile.data.network.model.RaspDevicesModel
import ru.spiridonov.smartservermobile.data.network.model.UserResponseModel
import ru.spiridonov.smartservermobile.domain.entity.DevTypes
import ru.spiridonov.smartservermobile.domain.entity.RaspDevices
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

    fun mapRaspDevJsonContainerToRaspDev(jsonContainer: RaspDevicesModel) =
        RaspDevices(
            id = jsonContainer.id,
            devType = jsonContainer.devType.toRaspDevType(),
            description = jsonContainer.description,
        )
}

fun List<String>.toRolesSet(): Set<Roles> {
    val rolesSet = mutableSetOf<Roles>()
    Log.d("DtoMapper", "toRolesSet: $this")
    this.forEach {
        when (it) {
            "ROLE_USER" -> rolesSet.add(Roles.ROLE_USER)
            "ROLE_ADMIN" -> rolesSet.add(Roles.ROLE_ADMIN)
        }
    }
    return rolesSet
}

fun String.toRaspDevType(): DevTypes {
    return when (this) {
        "FAN" -> DevTypes.FAN
        "CONDITIONER" -> DevTypes.CONDITIONER
        "LIGHT" -> DevTypes.LIGHT
        "TEMP_SENSOR" -> DevTypes.TEMP_SENSOR
        "BOX_TEMP_SENSOR" -> DevTypes.BOX_TEMP_SENSOR
        "TEMP_HUMIDITY_SENSOR" -> DevTypes.TEMP_HUMIDITY_SENSOR
        "SECURITY_SENSOR" -> DevTypes.SECURITY_SENSOR
        else -> DevTypes.FAN
    }
}