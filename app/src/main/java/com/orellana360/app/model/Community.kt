package com.orellana360.app.model

data class Community(
    val id: Int,
    val name: String,
    val description: String,
    val location: String,
    val distance: String,
    val tags: List<String>,
    val activities: List<String> = emptyList(),
    val shortName: String,
    val imageRes: Int = 0
)
