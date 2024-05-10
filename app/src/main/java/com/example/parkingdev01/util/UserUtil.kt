package com.example.parkingdev01.util

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserExistsResponse(
    @Json(name = "items") val items: List<UserItem>
)

@JsonClass(generateAdapter = true)
data class UserItem(
    @Json(name = "response") val response: Int
)