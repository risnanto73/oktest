package com.tiorisnanto.myapplication.dao

import androidx.room.Dao
import androidx.room.Insert


@Dao
interface CodeDao {

    @Insert
    suspend fun addCode(code: Code)
}