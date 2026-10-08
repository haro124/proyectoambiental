package com.orellana360.app.ui.tourism

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orellana360.app.databinding.ItemSitePhotoBinding
import com.orellana360.app.util.SiteImages

/** Galería horizontal de fotos del sitio. Recibe rutas de assets. */
class GalleryAdapter(
    private val photos: List<String>,
    private val onPhotoClick: (Int) -> Unit = {}
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSitePhotoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position], position)
    }

    override fun getItemCount(): Int = photos.size

    inner class ViewHolder(
        private val binding: ItemSitePhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(assetPath: String, position: Int) {
            SiteImages.load(binding.ivSitePhoto, assetPath)
            binding.root.setOnClickListener { onPhotoClick(position) }
        }
    }
}
