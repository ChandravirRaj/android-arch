package com.androboy.androidarch.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androboy.androidarch.db.dao.UserDao
import com.androboy.androidarch.db.entities.User


@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}