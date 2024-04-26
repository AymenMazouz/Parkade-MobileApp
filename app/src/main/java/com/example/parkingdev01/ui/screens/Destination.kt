package com.example.parkingdev01.ui.screens

sealed class Destination(val route: String) {
    
    object Login : Destination("login")
    object SignUp : Destination("signup")
    object Dashboard : Destination("dashboard")
    object Map : Destination("map")
    object Parkings : Destination("parkings")
    object Reservations : Destination("reservations")
    object Profile : Destination("profile")
}