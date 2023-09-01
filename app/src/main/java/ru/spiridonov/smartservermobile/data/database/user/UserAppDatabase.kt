package ru.spiridonov.smartservermobile.data.database.user

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class UserAppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: UserAppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "user.db"

        fun getInstance(context: Context): UserAppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    context,
                    UserAppDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}