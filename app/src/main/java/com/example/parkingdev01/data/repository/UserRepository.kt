

    package com.example.parkingdev01.data.repository

    import com.example.parkingdev01.data.remote.RetrofitInstance
    import com.example.parkingdev01.util.UserExistsResponse
    import com.squareup.moshi.JsonAdapter
    import com.squareup.moshi.Moshi
    import com.squareup.moshi.Types
    import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

    class UserRepository {

        private val userApi = RetrofitInstance.userApi

        suspend fun authenticate(email: String, password: String): Boolean {


            val response = userApi.authenticate(email, password)


            if (response.isSuccessful) {

                val jsonResponse = response.body()?.string()
                println("JSON Response: $jsonResponse") // Print JSON response for debugging
                jsonResponse?.let {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val jsonAdapter: JsonAdapter<UserExistsResponse> = moshi.adapter(UserExistsResponse::class.java)
                    val userExistsResponse = jsonAdapter.fromJson(jsonResponse)
                    // Check if the response attribute inside items is equal to 1
                    return userExistsResponse?.items?.getOrNull(0)?.response == 1
                }
            }
            return false
        }

        suspend fun createUser(
            email: String,
            password: String,
            firstName: String,
            lastName: String,
            phoneNumber: String,
            photoUrl: String
        ): Boolean {
            val response = userApi.signUp(
                email = email, password = password, firstName =  firstName, lastName =  lastName, phoneNumber, photoUrl
            )
            if (response.isSuccessful) {
                val jsonResponse = response.body()?.string()
                println("JSON Response: $jsonResponse") // Print JSON response for debugging
                jsonResponse?.let {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val type = Types.newParameterizedType(Map::class.java, String::class.java, String::class.java)
                    val jsonAdapter: JsonAdapter<Map<String, String>> = moshi.adapter(type)
                    val responseObject: Map<String, String>? = jsonAdapter.fromJson(jsonResponse)
                    // Check if output_message is "success"
                    return responseObject?.get("output_message") == "success"
                }
            }
            return false
        }


    }
