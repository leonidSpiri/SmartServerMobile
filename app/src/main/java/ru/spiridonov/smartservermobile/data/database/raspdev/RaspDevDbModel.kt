package ru.spiridonov.smartservermobile.data.database.raspdev

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rasp_dev")
data class RaspDevDbModel(
    @PrimaryKey
    val id: Long,
    val devType: String,
    val description: String
)
