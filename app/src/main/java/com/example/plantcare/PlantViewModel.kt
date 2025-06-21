package com.example.plantcare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.model.Plant
import com.example.plantcare.repository.PlantRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class PlantViewModel : ViewModel() {
    private val repository = PlantRepository()

    val plants: StateFlow<List<Plant>> = repository.plants

    fun addPlant(plant: Plant) {
        viewModelScope.launch {
            Log.d("PlantViewModel", "Adding plant: ${plant.name}")
            repository.addPlant(plant)
        }
    }

    fun updatePlant(plant: Plant) {
        viewModelScope.launch {
            Log.d("PlantViewModel", "Updating plant: ${plant.name}")
            repository.updatePlant(plant)
        }
    }

    fun deletePlant(plantId: Int) {
        viewModelScope.launch {
            Log.d("PlantViewModel", "Deleting plant with id: $plantId")
            repository.deletePlant(plantId)
        }
    }

    fun waterPlant(plantId: Int) {
        viewModelScope.launch {
            Log.d("PlantViewModel", "Watering plant with id: $plantId")
            repository.waterPlant(plantId)
        }
    }

    fun fertilizePlant(plantId: Int) {
        viewModelScope.launch {
            Log.d("PlantViewModel", "Fertilizing plant with id: $plantId")
            repository.fertilizePlant(plantId)
        }
    }

    fun getPlantById(id: Int): Plant? {
        return repository.getPlantById(id)
    }
}