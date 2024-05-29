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


data class UserGetResponse(
    val items: List<UserGetItem>
)

data class UserGetItem(
    val id: Int,
    val first_name : String,
    val last_name : String,
    val email: String,
    val password: String,
    val phone_number: String,
    val photo_url : String
)