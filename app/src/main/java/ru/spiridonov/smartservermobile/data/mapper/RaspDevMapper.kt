package ru.spiridonov.smartservermobile.data.mapper

import ru.spiridonov.smartservermobile.data.database.raspdev.RaspDevDbModel
import ru.spiridonov.smartservermobile.domain.entity.RaspDevices
import javax.inject.Inject

class RaspDevMapper @Inject constructor() {
    fun mapRaspDevToRaspDevDbModel(raspDevices: RaspDevices) = RaspDevDbModel(
        id = raspDevices.id,
        devType = raspDevices.devType.toString(),
        description = raspDevices.description
    )

    fun mapRaspDevDbModelToRaspDev(raspDevDbModel: RaspDevDbModel) = RaspDevices(
        id = raspDevDbModel.id,
        devType = raspDevDbModel.devType.toRaspDevType(),
        description = raspDevDbModel.description
    )
}