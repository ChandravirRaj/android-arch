package com.androboy.androidarch.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.androboy.androidarch.constant.AppConstant
import com.androboy.androidarch.db.dao.UserDao
import com.androboy.androidarch.db.entities.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        private var INSTANCE: AppDatabase? = null
        fun getDbInstance(context: Context): AppDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context, AppDatabase::class.java, AppConstant.dbName
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }

}