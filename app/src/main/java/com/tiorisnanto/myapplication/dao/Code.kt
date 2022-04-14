package com.tiorisnanto.myapplication.dao

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Code(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,

    )
