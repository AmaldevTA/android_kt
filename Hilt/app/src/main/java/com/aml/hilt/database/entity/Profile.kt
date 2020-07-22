package com.aml.hilt.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Profile (

    @PrimaryKey
    @SerializedName("ID")
    var id: Int,

    @SerializedName("USER_ID")
    @Expose
    val userID: String,

    @SerializedName("USER_NAME")
    @Expose
    val userName: String
)