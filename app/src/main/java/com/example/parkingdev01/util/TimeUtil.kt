package com.example.parkingdev01.util

import java.text.SimpleDateFormat
import java.util.Date

object TimeUtil {
    private const val TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss"

    fun formatTimestamp(timestamp: Long): String {
        val dateFormat = SimpleDateFormat(TIMESTAMP_FORMAT)
        val date = Date(timestamp)
        return dateFormat.format(date)
    }
}