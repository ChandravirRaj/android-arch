package com.androboy.androidarch.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id: Int,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val email: String
)
