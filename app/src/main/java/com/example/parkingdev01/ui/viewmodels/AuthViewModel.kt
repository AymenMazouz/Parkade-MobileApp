package com.example.parkingdev01.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.parkingdev01.data.model.User
import com.example.parkingdev01.data.repository.UserRepository
import com.example.parkingdev01.util.Constants
import com.example.parkingdev01.util.PreferencesManager

class AuthViewModel(
    private val userRepository: UserRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    suspend fun login(email: String, password: String): Boolean {
        var isSuccess = userRepository.authenticate(email, password)

        if (isSuccess) {
            val user: User? = userRepository.getDetails(email, password)
            user?.let { preferencesManager.saveUser(it) }
            isSuccess = saveToken(Constants.APP_TOKEN)

        }

        return isSuccess
    }

    suspend fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photoUrl: String
    ): Boolean {
        var isSuccess = userRepository.createUser(
            email,
            password,
            firstName,
            lastName,
            phoneNumber,
            photoUrl
        )

        if (isSuccess) {
            val user: User? = userRepository.getDetails(email, password)
            user.let { preferencesManager.saveUser(it!!) }
            isSuccess = saveToken(Constants.APP_TOKEN)
        }

        return isSuccess
    }


    private suspend fun saveToken(token: String): Boolean{
        val userId = preferencesManager.getUsedId()
        if (userId != -1){
            return userRepository.saveToken(userId, token)
        }
        return false
    }
}
