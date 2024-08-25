package com.example.weatherappassignment.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherappassignment.data.User
import com.example.weatherappassignment.database.UserDatabase
import com.example.weatherappassignment.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = UserDatabase.getDatabase(application).userDao()
    private val repository: UserRepository = UserRepository(userDao)

    val allUsers: LiveData<List<User>> = repository.allUsers.asLiveData()

    fun insertUser(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }

}