package com.plcoding.mapscomposeguide.presentation
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.parkingdev01.ui.screens.dashboard.map.MapEvent
import com.example.parkingdev01.ui.screens.dashboard.map.MapState
import com.example.parkingdev01.ui.screens.dashboard.map.MapStyle
import com.google.android.gms.maps.model.MapStyleOptions

class MapsViewModel: ViewModel(){
    var state by mutableStateOf(MapState())


    fun onEvent (event: MapEvent){
        when(event){
            is MapEvent.ToggleFalloutMap -> {
                state=state.copy(
                    properties = state.properties.copy(
                        mapStyleOptions = if (state.isFalloutMap){
                            null
                        }else MapStyleOptions(MapStyle.JSON),
                    ),
                    isFalloutMap = !state.isFalloutMap
                )
            }
        }
    }
}