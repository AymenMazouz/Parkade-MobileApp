package com.example.parkingdev01.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.parkingdev01.data.repository.NotificationRepository

class NotificationViewModel(private val repository: NotificationRepository) : ViewModel() {

    suspend fun scheduleNotification(
        userId: Int, parkingName: String, time: Long
    ): Boolean {
        val tokens = repository.getUserTokens(userId)
        var finalSuccess = true
        var tempSuccess: Boolean
        for (token in tokens) {
            tempSuccess = repository.scheduleNotification(
                token,
                "Reservation Reminder",
                "Your reservation for $parkingName is starting soon",
                time
            )
            if (!tempSuccess && finalSuccess) {
                finalSuccess = false
            }
        }
        return finalSuccess
    }
}
