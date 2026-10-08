package com.orellana360.app.model

data class Species(
    val id: Int,
    val name: String,
    val scientificName: String,
    val description: String,
    val status: String,
    val habitat: String,
    val sightings: Int,
    val altitude: String,
    val imageRes: Int = 0
)
