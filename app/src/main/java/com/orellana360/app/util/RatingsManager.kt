package com.orellana360.app.util

/**
 * Guarda las evaluaciones que el usuario deja en la ficha de cada atractivo.
 *
 * Sigue el mismo patrón en memoria que [FavoritesManager]: los datos se pierden
 * al cerrar la app. Cuando el proyecto tenga backend o base local, este objeto
 * es el único punto que hay que cambiar.
 */
object RatingsManager {

    data class Review(val rating: Float, val comment: String)

    private val reviews = mutableMapOf<Int, MutableList<Review>>()

    fun add(attractionId: Int, rating: Float, comment: String) {
        reviews.getOrPut(attractionId) { mutableListOf() }
            .add(Review(rating, comment.trim()))
    }

    fun count(attractionId: Int): Int = reviews[attractionId]?.size ?: 0

    /** Promedio de las evaluaciones del usuario, o null si el sitio aún no tiene ninguna. */
    fun average(attractionId: Int): Float? {
        val list = reviews[attractionId]?.takeIf { it.isNotEmpty() } ?: return null
        return list.map { it.rating }.average().toFloat()
    }

    /**
     * Texto de calificación para la lista y la ficha: promedio de las evaluaciones
     * del usuario si existen, si no el rating base del dato, y si tampoco hay, "Sin calificar".
     */
    fun labelFor(attractionId: Int, baseRating: Double): String {
        val average = average(attractionId)
        return when {
            average != null -> "\u2605 %.1f".format(average)
            baseRating > 0.0 -> "\u2605 %.1f".format(baseRating)
            else -> "Sin calificar"
        }
    }

    fun reviewsOf(attractionId: Int): List<Review> = reviews[attractionId]?.toList() ?: emptyList()

    fun clear() = reviews.clear()
}
