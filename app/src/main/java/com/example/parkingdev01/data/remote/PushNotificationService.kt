package com.example.parkingdev01.data.remote

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.parkingdev01.R
import com.example.parkingdev01.data.repository.UserRepository
import com.example.parkingdev01.ui.viewmodels.AuthViewModel
import com.example.parkingdev01.util.Constants
import com.example.parkingdev01.util.PreferencesManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PushNotificationService(): FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Send the token to the server for future use
        Log.d("FCM", "New token: $token")

        Constants.APP_TOKEN = token

    }
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("FCM", "Message received: ${message.data}")

    }




}