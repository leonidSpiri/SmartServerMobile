package ru.spiridonov.smartservermobile.data.mapper

import android.util.Log
import ru.spiridonov.smartservermobile.data.network.model.RaspDevicesModel
import ru.spiridonov.smartservermobile.data.network.model.RaspStateModel
import ru.spiridonov.smartservermobile.data.network.model.SecurityModel
import ru.spiridonov.smartservermobile.data.network.model.UserResponseModel
import ru.spiridonov.smartservermobile.domain.entity.DevTypes
import ru.spiridonov.smartservermobile.domain.entity.RaspDevices
import ru.spiridonov.smartservermobile.domain.entity.RaspState
import ru.spiridonov.smartservermobile.domain.entity.Roles
import ru.spiridonov.smartservermobile.domain.entity.Security
import ru.spiridonov.smartservermobile.domain.entity.User
import ru.spiridonov.smartservermobile.domain.usecases.raspdevices.GetRaspDeviceByTypeUseCase
import java.time.OffsetDateTime
import javax.inject.Inject

class DtoMapper @Inject constructor(
    //private val getRaspDeviceByTypeUseCase: GetRaspDeviceByTypeUseCase
) {
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

    suspend fun mapRaspStateJsonContainerToRaspState(
        jsonContainer: RaspStateModel,
        getRaspDeviceByTypeUseCase: GetRaspDeviceByTypeUseCase
    ): RaspState {
        val statesList = mutableListOf<Pair<RaspDevices, String>>()
        jsonContainer.raspState.split(", ").forEach {
            val raspDev =
                getRaspDeviceByTypeUseCase.invoke(it.substringBefore(":")) ?: return@forEach
            statesList.add(Pair(raspDev, it.substringAfter(":")))
        }

        val date = OffsetDateTime.parse(jsonContainer.dateTime).plusHours(3)
        return RaspState(
            id = jsonContainer.id,
            dateTime = date,
            raspState = statesList,
            isSecurityViolated = jsonContainer.isSecurityViolated
        )
    }

    fun mapSecurityJsonContainerToSecurity(jsonContainer: SecurityModel): Security {
        val date = OffsetDateTime.parse(jsonContainer.dateTime).plusHours(3)
        return Security(
            id = jsonContainer.id,
            dateTime = date,
            user = User(
                id = jsonContainer.user.id,
                userName = jsonContainer.user.username,
                email = "",
                roles = emptySet()
            ),
            isSecurityTurnOn = jsonContainer.isSecurityTurnOn
        )
    }
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