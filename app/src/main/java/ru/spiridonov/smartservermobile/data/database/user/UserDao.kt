package ru.spiridonov.smartservermobile.data.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(feederItemDbModel: UserDbModel)

    @Query("SELECT * FROM user")
    fun getUser(): UserDbModel

    @Query("SELECT refreshToken FROM user")
    fun getUserRefreshToken(): String
}