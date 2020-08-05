package com.aml.room.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (

    @PrimaryKey
    @ColumnInfo(name = "id")
    var userId: Int,

    val name: String,

    val age: Int
)