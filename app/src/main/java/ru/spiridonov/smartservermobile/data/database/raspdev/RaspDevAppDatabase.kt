package ru.spiridonov.smartservermobile.data.database.raspdev

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.spiridonov.smartservermobile.data.database.user.UserDbModel

@Database(entities = [RaspDevDbModel::class], version = 1, exportSchema = false)
abstract class RaspDevAppDatabase : RoomDatabase() {
    abstract fun raspDevDao(): RaspDevDao

    companion object {
        private var INSTANCE: RaspDevAppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "rasp_dev.db"

        fun getInstance(context: Context): RaspDevAppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    context,
                    RaspDevAppDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}