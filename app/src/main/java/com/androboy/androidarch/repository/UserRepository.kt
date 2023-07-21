package com.androboy.androidarch.repository

import androidx.lifecycle.LiveData
import com.androboy.androidarch.db.dao.UserDao
import com.androboy.androidarch.db.entities.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    fun getUsers(): LiveData<List<User>> {
        return userDao.getUsers()
    }
}