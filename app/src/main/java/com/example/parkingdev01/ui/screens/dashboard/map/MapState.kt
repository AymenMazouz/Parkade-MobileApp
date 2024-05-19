package com.example.parkingdev01.ui.screens.dashboard.map

import com.google.maps.android.compose.MapProperties

data class MapState(
    val properties :MapProperties = MapProperties(),
    val isFalloutMap :Boolean=false
)
