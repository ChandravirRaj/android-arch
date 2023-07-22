package com.androboy.androidarch.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.androboy.androidarch.db.entities.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT * from user ")
    fun getUsers(): LiveData<List<User>>
}