package com.orellana360.app.model

data class Attraction(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val location: String,
    val distance: String,
    val rating: Double,
    val imageRes: Int = 0,
    /** Carpeta dentro de assets/sitios que contiene las fotos del sitio. */
    val imageFolder: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val activities: List<String> = emptyList(),
    val biodiversity: List<String> = emptyList(),
    val culture: String = "",
    val howToGet: String = "",
    val services: List<String> = emptyList()
)
