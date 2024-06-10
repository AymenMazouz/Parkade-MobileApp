package com.example.parkingdev01.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

data class NotificationRequest(
    val token: String,
    val title: String,
    val body: String,
    val time: Long
)

data class NotificationResponse(
    val status: String
)

interface NotificationApi {
    @POST("schedule_notification")
    suspend fun scheduleNotification(@Body request: NotificationRequest): NotificationResponse
}
