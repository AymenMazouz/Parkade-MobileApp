

package com.example.parkingdev01.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.parkingdev01.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class LoginViewModel (private val userRepository: UserRepository) : ViewModel() {

    suspend fun login(email: String, password: String): Boolean {
        return userRepository.checkUserExistence(email, password)
    }
}