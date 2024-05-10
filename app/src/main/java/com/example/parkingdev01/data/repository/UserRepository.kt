

package com.example.parkingdev01.data.repository

import com.example.parkingdev01.data.remote.UserApi
import com.example.parkingdev01.util.UserExistsResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.example.parkingdev01.data.remote.RetrofitInstance
import javax.inject.Inject
import javax.inject.Singleton

class UserRepository {

    private val userApi = RetrofitInstance.userApi

    suspend fun checkUserExistence(email: String, password: String): Boolean {
        val response = userApi.isUserExists(email, password)
        if (response.isSuccessful) {
            val jsonResponse = response.body()?.string()
            println("JSON Response: $jsonResponse") // Print JSON response for debugging
            jsonResponse?.let {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val jsonAdapter: JsonAdapter<UserExistsResponse> = moshi.adapter(UserExistsResponse::class.java)
                val userExistsResponse = jsonAdapter.fromJson(jsonResponse)
                // Check if the response attribute inside items is equal to 1
                return userExistsResponse?.items?.getOrNull(0)?.response == 1
            }
        }
        return false
    }
}
