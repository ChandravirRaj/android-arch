package com.androboy.androidarch.repository

import androidx.lifecycle.LiveData
import com.androboy.androidarch.db.dao.UserDao
import com.androboy.androidarch.db.entities.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    fun getUsers(): LiveData<List<User>> {
        return userDao.getUsers()
    }
}