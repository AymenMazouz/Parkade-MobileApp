package com.example.parkingdev01.data.repository

import android.util.Log
import com.example.parkingdev01.data.remote.NotificationApi
import com.example.parkingdev01.data.remote.NotificationRequest
import com.example.parkingdev01.data.remote.NotificationResponse
import com.example.parkingdev01.data.remote.RetrofitInstance
import com.example.parkingdev01.data.remote.RetrofitInstance.userApi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.HttpException
import java.io.IOException

class NotificationRepository {

    private val notificationApi: NotificationApi = RetrofitInstance.notificationApi

    suspend fun scheduleNotification(
        token: String,
        title: String,
        body: String,
        time: Long
    ): Boolean {
        val request = NotificationRequest(token, title, body, time)
        return try {
            val response: NotificationResponse = notificationApi.scheduleNotification(request)
            response.status == "success"
        } catch (e: IOException) {
            false
        } catch (e: HttpException) {
            // Log the error for debugging
            Log.e("NotificationRepo", "HTTP error: ${e.code()}: ${e.message()}")
            false
        }
    }



    suspend fun getUserTokens(userId: Int): List<String> {
        val response = userApi.getUserTokens(userId)
        if (response.isSuccessful) {
            val jsonResponse = response.body()?.string()
            println("JSON Response: $jsonResponse") // Print JSON response for debugging
            jsonResponse?.let {
                val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                val type = Types.newParameterizedType(
                    Map::class.java,
                    String::class.java,
                    Any::class.java // Change the value type to Any since the values in "items" are objects
                )
                val jsonAdapter: JsonAdapter<Map<String, Any>> = moshi.adapter(type)
                val responseObject: Map<String, Any>? = jsonAdapter.fromJson(jsonResponse)

                // Extract tokens from "items" array
                val items = responseObject?.get("items") as? List<Map<String, Any>>
                val tokens = items?.mapNotNull { it["token"] as? String }
                return tokens ?: emptyList()
            }
        }
        return emptyList() // Return an empty list if the response is not successful
    }

}

