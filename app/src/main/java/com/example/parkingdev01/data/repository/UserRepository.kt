package com.example.parkingdev01.data.repository

import com.example.parkingdev01.data.model.User
import com.example.parkingdev01.data.remote.RetrofitInstance
import com.example.parkingdev01.util.UserExistsResponse
import com.example.parkingdev01.util.UserGetResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.HttpException

class UserRepository {

    private val userApi = RetrofitInstance.userApi

    suspend fun authenticate(email: String, password: String): Boolean {
        val response = userApi.authenticate(email, password)


        if (response.isSuccessful) {

            val jsonResponse = response.body()?.string()
            println("JSON Response: $jsonResponse") // Print JSON response for debugging
            jsonResponse?.let {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val jsonAdapter: JsonAdapter<UserExistsResponse> =
                    moshi.adapter(UserExistsResponse::class.java)
                val userExistsResponse = jsonAdapter.fromJson(jsonResponse)
                // Check if the response attribute inside items is equal to 1
                return userExistsResponse?.items?.getOrNull(0)?.response == 1
            }
        }
        return false
    }


    suspend fun createUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        photoUrl: String
    ): Boolean {
        val response = userApi.signUp(
            email = email,
            password = password,
            firstName = firstName,
            lastName = lastName,
            phoneNumber,
            photoUrl
        )
        if (response.isSuccessful) {
            val jsonResponse = response.body()?.string()
            println("JSON Response: $jsonResponse") // Print JSON response for debugging
            jsonResponse?.let {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val type = Types.newParameterizedType(
                    Map::class.java,
                    String::class.java,
                    String::class.java
                )
                val jsonAdapter: JsonAdapter<Map<String, String>> = moshi.adapter(type)
                val responseObject: Map<String, String>? = jsonAdapter.fromJson(jsonResponse)
                // Check if output_message is "success"
                return responseObject?.get("output_message") == "success"
            }
        }
        return false
    }


    suspend fun getDetails(email: String, password: String): User? {

        val response = userApi.getDetails(email, password)


        if (response.isSuccessful) {
            val jsonResponse = response.body()?.string()
            println("JSON Response: $jsonResponse") // Print JSON response for debugging

            return jsonResponse?.let {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

                val jsonAdapter: JsonAdapter<UserGetResponse> =
                    moshi.adapter(UserGetResponse::class.java)
                val parkingResponse = jsonAdapter.fromJson(jsonResponse)

                parkingResponse?.items?.firstOrNull()?.let { userItem ->
                    User(
                        id = userItem.id,
                        email = userItem.email,
                        password = userItem.password,
                        firstName = userItem.first_name,
                        lastName = userItem.last_name,
                        phoneNumber = userItem.phone_number,
                        photoUrl = userItem.photo_url
                    )
                }
            }
        } else {
            throw HttpException(response)
        }
    }


    suspend fun saveToken(userId: Int, token: String): Boolean {
        val response = userApi.saveToken(
            userId = userId,
            token = token
        )
        if (response.isSuccessful) {
            val jsonResponse = response.body()?.string()
            println("JSON Response: $jsonResponse") // Print JSON response for debugging
            jsonResponse?.let {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val type = Types.newParameterizedType(
                    Map::class.java,
                    String::class.java,
                    String::class.java
                )
                val jsonAdapter: JsonAdapter<Map<String, String>> = moshi.adapter(type)
                val responseObject: Map<String, String>? = jsonAdapter.fromJson(jsonResponse)
                // Check if output_message is "success"
                val outputMessage = responseObject?.get("output_message")
                return outputMessage == "success" || outputMessage == "ignored"
            }
        }
        return false
    }




}
