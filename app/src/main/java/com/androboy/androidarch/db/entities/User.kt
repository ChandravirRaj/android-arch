package com.androboy.androidarch.db.entities

import androidx.room.Entity

@Entity(tableName = "user")
data class User(val firstName: String, val lastName: String, val age: Int, val email: String)
