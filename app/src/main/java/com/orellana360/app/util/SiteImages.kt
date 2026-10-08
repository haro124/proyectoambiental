package com.orellana360.app.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.LruCache
import android.widget.ImageView
import java.io.IOException
import java.util.concurrent.Executors

/**
 * Carga las fotos de los sitios turísticos desde `assets/sitios/<carpeta>`.
 *
 * Las fotos originales viven en la carpeta `sitiosturisticos/` de la raíz del proyecto;
 * el script de conversión las reescala y copia a assets con nombres ordenados (01.jpg, 02.jpg, …).
 * Se decodifican con `inSampleSize` y en un hilo aparte para no agotar memoria ni bloquear la UI.
 */
object SiteImages {

    private const val BASE = "sitios"

    private val pathCache = mutableMapOf<String, List<String>>()
    private val bitmapCache = object : LruCache<String, Bitmap>(12 * 1024 * 1024) {
        override fun sizeOf(key: String, value: Bitmap): Int = value.byteCount
    }
    private val executor = Executors.newFixedThreadPool(2)
    private val mainHandler = Handler(Looper.getMainLooper())

    /** Rutas de assets de las fotos del sitio, en orden. Lista vacía si el sitio no tiene fotos. */
    fun pathsFor(context: Context, folder: String): List<String> {
        if (folder.isBlank()) return emptyList()
        pathCache[folder]?.let { return it }
        val paths = try {
            context.assets.list("$BASE/$folder")
                ?.filter { it.endsWith(".jpg", true) || it.endsWith(".png", true) }
                ?.sorted()
                ?.map { "$BASE/$folder/$it" }
                ?: emptyList()
        } catch (e: IOException) {
            emptyList()
        }
        pathCache[folder] = paths
        return paths
    }

    /** Foto principal del sitio (la primera de la carpeta), o null si no hay. */
    fun coverFor(context: Context, folder: String): String? =
        pathsFor(context, folder).firstOrNull()

    /**
     * Carga [assetPath] en [target] de forma asíncrona.
     * Usa el tag de la vista para descartar resultados de un reciclaje anterior.
     */
    fun load(target: ImageView, assetPath: String, reqWidth: Int = 1080) {
        val key = "$assetPath@$reqWidth"
        target.tag = key

        bitmapCache.get(key)?.let {
            target.setImageBitmap(it)
            return
        }

        val context = target.context.applicationContext
        executor.execute {
            val bitmap = decode(context, assetPath, reqWidth) ?: return@execute
            bitmapCache.put(key, bitmap)
            mainHandler.post {
                if (target.tag == key) target.setImageBitmap(bitmap)
            }
        }
    }

    private fun decode(context: Context, assetPath: String, reqWidth: Int): Bitmap? = try {
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        context.assets.open(assetPath).use { BitmapFactory.decodeStream(it, null, bounds) }

        val options = BitmapFactory.Options().apply {
            inSampleSize = sampleSizeFor(bounds.outWidth, reqWidth)
            inPreferredConfig = Bitmap.Config.RGB_565
        }
        context.assets.open(assetPath).use { BitmapFactory.decodeStream(it, null, options) }
    } catch (e: IOException) {
        null
    } catch (e: OutOfMemoryError) {
        null
    }

    private fun sampleSizeFor(sourceWidth: Int, reqWidth: Int): Int {
        if (sourceWidth <= 0 || reqWidth <= 0) return 1
        var sample = 1
        while (sourceWidth / (sample * 2) >= reqWidth) sample *= 2
        return sample
    }
}
