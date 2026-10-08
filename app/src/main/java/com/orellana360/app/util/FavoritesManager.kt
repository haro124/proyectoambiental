package com.orellana360.app.util

object FavoritesManager {
    private val favoriteIds = mutableSetOf<Int>()

    fun isFavorite(id: Int): Boolean = favoriteIds.contains(id)

    fun toggleFavorite(id: Int): Boolean {
        return if (favoriteIds.contains(id)) {
            favoriteIds.remove(id)
            false
        } else {
            favoriteIds.add(id)
            true
        }
    }

    fun getFavorites(): Set<Int> = favoriteIds.toSet()

    fun clear() {
        favoriteIds.clear()
    }
}
