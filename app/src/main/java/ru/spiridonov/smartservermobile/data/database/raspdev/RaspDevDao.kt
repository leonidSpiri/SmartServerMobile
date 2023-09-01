package ru.spiridonov.smartservermobile.data.database.raspdev

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RaspDevDao {

    @Query("SELECT * FROM rasp_dev")
    fun getAll(): List<RaspDevDbModel>?

    @Query("SELECT * FROM rasp_dev WHERE devType = :type LIMIT 1")
    fun getByType(type: String): RaspDevDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(raspDev: RaspDevDbModel)

    @Query("DELETE FROM rasp_dev")
    fun deleteAll()
}