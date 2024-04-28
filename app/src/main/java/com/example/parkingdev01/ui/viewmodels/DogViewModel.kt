package com.example.parkingdev01.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkingdev01.data.model.Dog
import com.example.parkingdev01.data.repository.DogRepository
import kotlinx.coroutines.launch

class DogViewModel(private val repository: DogRepository) : ViewModel() {
    val dogs = mutableStateOf<List<Dog>>(emptyList())

    init {
        fetchDogs()
    }

    private fun fetchDogs() {
        viewModelScope.launch {
            dogs.value = repository.getDogsFromRemote()
        }
    }
}