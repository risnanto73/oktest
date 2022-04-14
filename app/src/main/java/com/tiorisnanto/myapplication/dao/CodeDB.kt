package com.tiorisnanto.myapplication.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Code::class],
    version = 1
)
abstract class CodeDB : RoomDatabase() {

    abstract fun codeDao(): CodeDao

    companion object {

        @Volatile
        private var instance: CodeDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CodeDB::class.java,
            "codeqr.db"
        ).build()
    }


}

