    package com.example.parkingdev01.ui.viewmodels

    import androidx.lifecycle.ViewModel
    import com.example.parkingdev01.data.repository.NotificationRepository

    class NotificationViewModel(private val repository: NotificationRepository) : ViewModel() {

        suspend fun scheduleNotification(
            token: String,
            title: String,
            body: String,
            time: Long
        ): Boolean {
            return repository.scheduleNotification(token, title, body, time)
        }
    }
