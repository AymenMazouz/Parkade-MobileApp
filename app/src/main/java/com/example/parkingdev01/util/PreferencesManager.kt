package com.example.parkingdev01.util

import android.content.Context
import android.content.SharedPreferences
import com.example.parkingdev01.data.model.User

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "UserPreferences",
        Context.MODE_PRIVATE
    )

    fun saveUser(user: User) {
        with(sharedPreferences.edit()) {
            putInt("id", user.id)
            putString("firstName", user.firstName)
            putString("lastName", user.lastName)
            putString("email", user.email)
            putString(
                "password",
                user.password
            ) // Note: Saving password in plaintext in SharedPreferences is not recommended for security reasons.
            putString("phoneNumber", user.phoneNumber)
            putString("photoUrl", user.photoUrl)
            apply()
        }
    }

    fun getUser(): User? {
        if (sharedPreferences.contains("id")) {
            val id = sharedPreferences.getInt("id", -1)
            return User(
                id,
                sharedPreferences.getString("firstName", "") ?: "",
                sharedPreferences.getString("lastName", "") ?: "",
                sharedPreferences.getString("email", "") ?: "",
                sharedPreferences.getString("password", "") ?: "",
                sharedPreferences.getString("phoneNumber", "") ?: "",
                sharedPreferences.getString("photoUrl", "") ?: ""
            )
        } else {
            return null
        }
    }

    fun clearUser() {
        sharedPreferences.edit().clear().apply()
    }
}
