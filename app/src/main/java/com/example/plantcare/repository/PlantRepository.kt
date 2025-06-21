package com.example.plantcare.repository

import com.example.plantcare.model.Plant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.util.Log

class PlantRepository {
    private val _plants = MutableStateFlow<List<Plant>>(getSamplePlants())
    val plants: StateFlow<List<Plant>> = _plants.asStateFlow()

    private var nextId = 4

    fun addPlant(plant: Plant) {
        val newPlant = plant.copy(id = nextId++)
        _plants.value = _plants.value + newPlant
        Log.d("PlantRepository", "Plant added. Total plants: ${_plants.value.size}")
    }

    fun updatePlant(plant: Plant) {
        _plants.value = _plants.value.map { if (it.id == plant.id) plant else it }
        Log.d("PlantRepository", "Plant updated: ${plant.name}")
    }

    fun deletePlant(plantId: Int) {
        val sizeBefore = _plants.value.size
        _plants.value = _plants.value.filter { it.id != plantId }
        Log.d("PlantRepository", "Plant deleted. Plants before: $sizeBefore, after: ${_plants.value.size}")
    }

    fun getPlantById(id: Int): Plant? {
        val plant = _plants.value.find { it.id == id }
        Log.d("PlantRepository", "Getting plant by id $id: ${plant?.name ?: "not found"}")
        return plant
    }

    fun waterPlant(plantId: Int) {
        val plant = getPlantById(plantId)
        Log.d("PlantRepository", "Attempting to water plant with id: $plantId")
        plant?.let {
            val updatedPlant = it.copy(lastWatered = System.currentTimeMillis())
            updatePlant(updatedPlant)
            Log.d("PlantRepository", "Plant ${it.name} watered. Last watered: ${updatedPlant.lastWatered}")
        } ?: Log.e("PlantRepository", "Plant with id $plantId not found for watering")
    }

    fun fertilizePlant(plantId: Int) {
        val plant = getPlantById(plantId)
        Log.d("PlantRepository", "Attempting to fertilize plant with id: $plantId")
        plant?.let {
            val updatedPlant = it.copy(lastFertilized = System.currentTimeMillis())
            updatePlant(updatedPlant)
            Log.d("PlantRepository", "Plant ${it.name} fertilized. Last fertilized: ${updatedPlant.lastFertilized}")
        } ?: Log.e("PlantRepository", "Plant with id $plantId not found for fertilizing")
    }

    private fun getSamplePlants(): List<Plant> {
        val currentTime = System.currentTimeMillis()
        // Создаем растения с разными временами последнего полива для тестирования
        return listOf(
            Plant(
                id = 1,
                name = "Фикус Бенджамина",
                species = "Ficus benjamina",
                wateringInterval = 7,
                fertilizingInterval = 30,
                location = "Гостиная",
                notes = "Любит яркий рассеянный свет",
                lastWatered = currentTime - (6 * 24 * 60 * 60 * 1000L), // 6 дней назад
                lastFertilized = currentTime - (25 * 24 * 60 * 60 * 1000L) // 25 дней назад
            ),
            Plant(
                id = 2,
                name = "Монстера",
                species = "Monstera deliciosa",
                wateringInterval = 5,
                fertilizingInterval = 21,
                location = "Спальня",
                notes = "Нуждается в опоре для роста",
                lastWatered = currentTime - (8 * 24 * 60 * 60 * 1000L), // 8 дней назад (нужен полив)
                lastFertilized = currentTime - (20 * 24 * 60 * 60 * 1000L) // 20 дней назад
            ),
            Plant(
                id = 3,
                name = "Сансевиерия",
                species = "Sansevieria trifasciata",
                wateringInterval = 14,
                fertilizingInterval = 60,
                location = "Кабинет",
                notes = "Очень неприхотливое растение",
                lastWatered = currentTime - (10 * 24 * 60 * 60 * 1000L), // 10 дней назад
                lastFertilized = currentTime - (65 * 24 * 60 * 60 * 1000L) // 65 дней назад (нужно удобрение)
            )
        )
    }
}