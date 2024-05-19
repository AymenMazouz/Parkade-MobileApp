package com.plcoding.mapscomposeguide.presentation

import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties

data class MapState(
    val propreties :MapProperties = MapProperties(),
    val isFalloutMap :Boolean=false
)
