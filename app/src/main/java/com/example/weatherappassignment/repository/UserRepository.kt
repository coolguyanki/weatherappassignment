package com.example.weatherappassignment.repository

import com.example.weatherappassignment.dao.UserDao
import com.example.weatherappassignment.data.User
import kotlinx.coroutines.flow.Flow

class UserRepository (private val userDao: UserDao) {

    val allUsers: Flow<List<User>> = userDao.getAllUsers()

    suspend fun insertUser(user: User) { userDao.insertUser(user) }

    suspend fun deleteUser(user: User) { userDao.deleteUser(user) }
}