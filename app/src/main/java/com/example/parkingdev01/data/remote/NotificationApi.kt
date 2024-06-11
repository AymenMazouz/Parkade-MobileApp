package com.example.parkingdev01.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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
