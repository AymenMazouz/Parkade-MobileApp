package com.example.parkingdev01.data.repository

import android.util.Log
import com.example.parkingdev01.data.remote.NotificationApi
import com.example.parkingdev01.data.remote.NotificationRequest
import com.example.parkingdev01.data.remote.NotificationResponse
import com.example.parkingdev01.data.remote.RetrofitInstance
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
}

