package com.orellana360.app.ui.tourism

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orellana360.app.R
import com.orellana360.app.databinding.ItemAttractionBinding
import com.orellana360.app.model.Attraction
import com.orellana360.app.util.RatingsManager
import com.orellana360.app.util.SiteImages

class TourismAdapter(
    private val onItemClick: (Attraction) -> Unit
) : ListAdapter<Attraction, TourismAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAttractionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemAttractionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(attraction: Attraction) {
            binding.tvAttractionName.text = attraction.name
            binding.tvAttractionLocation.text = attraction.subtitle()
            binding.tvAttractionTag.text = attraction.category
            binding.tvAttractionRating.text =
                RatingsManager.labelFor(attraction.id, attraction.rating)

            val cover = SiteImages.coverFor(binding.root.context, attraction.imageFolder)
            if (cover != null) {
                SiteImages.load(binding.ivAttractionIcon, cover, reqWidth = 320)
            } else {
                binding.ivAttractionIcon.setImageResource(R.drawable.ic_compass)
            }

            binding.root.setOnClickListener {
                onItemClick(attraction)
            }
        }

        /** Oculta la distancia mientras siga siendo un valor sin definir. */
        private fun Attraction.subtitle(): String =
            if (distance.isBlank() || distance.startsWith("Por definir")) location
            else "$location · $distance"
    }

    private class DiffCallback : DiffUtil.ItemCallback<Attraction>() {
        override fun areItemsTheSame(oldItem: Attraction, newItem: Attraction): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Attraction, newItem: Attraction): Boolean =
            oldItem == newItem
    }
}
