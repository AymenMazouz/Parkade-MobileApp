package com.example.parkingdev01.util

import com.example.parkingdev01.data.model.User

import android.content.Context
import android.net.ConnectivityManager

class Constants {
    companion object {
        fun isOnline(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

        var APP_TOKEN: String = ""
        var USER: User = User(
            -1,
            "",
            "",
            "",
            "",
            "",
            ""
        )
    }
}

