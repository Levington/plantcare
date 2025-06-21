package com.example.plantcare.model

data class Plant(
    val id: Int = 0,
    val name: String,
    val species: String,
    val wateringInterval: Int, // дни
    val lastWatered: Long = System.currentTimeMillis(),
    val fertilizingInterval: Int = 30, // дни
    val lastFertilized: Long = System.currentTimeMillis(),
    val notes: String = "",
    val imageUrl: String = "",
    val location: String = ""
) {
    fun getDaysUntilNextWatering(): Int {
        val daysSinceLastWatering = ((System.currentTimeMillis() - lastWatered) / (1000 * 60 * 60 * 24)).toInt()
        return maxOf(0, wateringInterval - daysSinceLastWatering)
    }

    fun getDaysUntilNextFertilizing(): Int {
        val daysSinceLastFertilizing = ((System.currentTimeMillis() - lastFertilized) / (1000 * 60 * 60 * 24)).toInt()
        return maxOf(0, fertilizingInterval - daysSinceLastFertilizing)
    }

    fun needsWatering(): Boolean = getDaysUntilNextWatering() == 0
    fun needsFertilizing(): Boolean = getDaysUntilNextFertilizing() == 0
}