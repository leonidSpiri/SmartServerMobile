package ru.spiridonov.smartservermobile.data.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userDbModel: UserDbModel)

    @Query("SELECT * FROM user")
    fun getUser(): UserDbModel?

    @Query("DELETE FROM user WHERE id = :userId")
    suspend fun deleteUser(userId: Long)
}