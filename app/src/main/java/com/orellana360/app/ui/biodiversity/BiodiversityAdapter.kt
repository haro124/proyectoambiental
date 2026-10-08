package com.orellana360.app.ui.biodiversity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orellana360.app.R
import com.orellana360.app.databinding.ItemSpeciesBinding
import com.orellana360.app.model.Species

class BiodiversityAdapter : ListAdapter<Species, BiodiversityAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSpeciesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemSpeciesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(species: Species) {
            binding.tvSpeciesName.text = species.name
            binding.tvSpeciesScientific.text = species.scientificName
            binding.tvSpeciesStatus.text = species.status

            val iconRes = when (species.id) {
                1 -> R.drawable.ic_parrot
                2 -> R.drawable.ic_jaguar
                else -> R.drawable.ic_leaf
            }
            binding.ivSpeciesIcon.setImageResource(iconRes)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Species>() {
        override fun areItemsTheSame(oldItem: Species, newItem: Species): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Species, newItem: Species): Boolean =
            oldItem == newItem
    }
}
