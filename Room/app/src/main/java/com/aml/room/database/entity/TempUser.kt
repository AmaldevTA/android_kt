package com.aml.room.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "temp_user")
data class TempUser (

    @PrimaryKey
    var userId: Int,

    val name: String,
)