package com.orellana360.app.ui.tourism

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.orellana360.app.R
import com.orellana360.app.data.SampleData
import com.orellana360.app.databinding.FragmentTourismDetailBinding
import com.orellana360.app.model.Attraction
import com.orellana360.app.util.FavoritesManager
import com.orellana360.app.util.RatingsManager
import com.orellana360.app.util.SiteImages

/**
 * Ficha del atractivo turístico.
 *
 * Muestra la galería de fotos del sitio (desde `assets/sitios/<carpeta>`), las secciones
 * Información / Actividades / Biodiversidad / Cultura, y al final Cómo llegar, Servicios
 * y Evaluación.
 */
class TourismDetailFragment : Fragment() {

    private var _binding: FragmentTourismDetailBinding? = null
    private val binding get() = _binding!!
    private var attractionId: Int = -1
    private var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTourismDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attractionId = arguments?.getInt("attractionId", -1) ?: -1
        val attraction = SampleData.attractions.find { it.id == attractionId }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        if (attraction == null) return

        isFavorite = FavoritesManager.isFavorite(attractionId)
        updateFavoriteIcon()

        binding.toolbar.title = attraction.name
        binding.tvDetailName.text = attraction.name
        binding.tvDetailCategory.text = attraction.category
        binding.tvDetailRating.text = RatingsManager.labelFor(attraction.id, attraction.rating)

        setupGallery(attraction)
        setupTabs()
        bindInfo(attraction)
        bindBulletList(binding.layoutTabActivities, attraction.activities)
        bindBulletList(binding.layoutTabBiodiversity, attraction.biodiversity)
        binding.tvDetailCulture.text = attraction.culture.ifBlank { getString(R.string.detail_no_data) }
        bindHowToGet(attraction)
        bindBulletList(binding.layoutServices, attraction.services)
        setupReviews()

        binding.btnFavorite.setOnClickListener {
            isFavorite = FavoritesManager.toggleFavorite(attractionId)
            updateFavoriteIcon()
        }

        binding.btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "¡Descubre ${attraction.name} en Orellana 360!\n${attraction.description}"
                )
            }
            startActivity(Intent.createChooser(shareIntent, "Compartir"))
        }
    }

    private fun setupGallery(attraction: Attraction) {
        val photos = SiteImages.pathsFor(requireContext(), attraction.imageFolder)

        if (photos.isEmpty()) {
            binding.rvGallery.visibility = View.GONE
            binding.ivDetailImage.visibility = View.VISIBLE
            binding.tvPhotoIndicator.visibility = View.GONE
            return
        }

        binding.rvGallery.visibility = View.VISIBLE
        binding.ivDetailImage.visibility = View.GONE
        binding.rvGallery.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvGallery.adapter = GalleryAdapter(photos)
        PagerSnapHelper().attachToRecyclerView(binding.rvGallery)

        binding.tvPhotoIndicator.visibility = View.VISIBLE
        updatePhotoIndicator(0, photos.size)
        binding.rvGallery.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val manager = recyclerView.layoutManager as? LinearLayoutManager ?: return
                val position = manager.findFirstVisibleItemPosition()
                if (position != RecyclerView.NO_POSITION) {
                    updatePhotoIndicator(position, photos.size)
                }
            }
        })
    }

    private fun updatePhotoIndicator(position: Int, total: Int) {
        binding.tvPhotoIndicator.text = getString(R.string.photo_counter, position + 1, total)
    }

    private fun setupTabs() {
        val tabs = listOf(
            R.string.tab_info to binding.layoutTabInfo,
            R.string.tab_activities to binding.layoutTabActivities,
            R.string.tab_biodiversity to binding.layoutTabBiodiversity,
            R.string.tab_culture to binding.layoutTabCulture
        )

        tabs.forEach { (titleRes, _) ->
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(titleRes))
        }

        fun showSection(index: Int) {
            tabs.forEachIndexed { i, (_, section) ->
                section.visibility = if (i == index) View.VISIBLE else View.GONE
            }
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) = showSection(tab.position)
            override fun onTabUnselected(tab: TabLayout.Tab) = Unit
            override fun onTabReselected(tab: TabLayout.Tab) = Unit
        })

        showSection(0)
    }

    private fun bindInfo(attraction: Attraction) {
        binding.tvDetailDescription.text = attraction.description
        binding.tvDetailLocation.text = "📍 ${attraction.location}"

        val distance = attraction.distance
        if (distance.isBlank() || distance.startsWith("Por definir")) {
            binding.tvDetailDistance.text = "Distancia por definir"
        } else {
            binding.tvDetailDistance.text = "A $distance del centro"
        }
    }

    /** Llena un contenedor con viñetas reutilizando `item_activity.xml`. */
    private fun bindBulletList(container: LinearLayout, items: List<String>) {
        container.removeAllViews()
        val values = items.ifEmpty { listOf(getString(R.string.detail_no_data)) }
        values.forEach { text ->
            val row = LayoutInflater.from(container.context)
                .inflate(R.layout.item_activity, container, false)
            row.findViewById<TextView>(R.id.tvActivity).text = text
            container.addView(row)
        }
    }

    private fun bindHowToGet(attraction: Attraction) {
        binding.tvDetailHowToGet.text =
            attraction.howToGet.ifBlank { getString(R.string.detail_no_data) }

        val hasCoordinates = attraction.latitude != 0.0 || attraction.longitude != 0.0
        binding.btnOpenMap.visibility = if (hasCoordinates) View.VISIBLE else View.GONE
        binding.btnOpenMap.setOnClickListener {
            val label = Uri.encode(attraction.name)
            val uri = Uri.parse(
                "geo:${attraction.latitude},${attraction.longitude}" +
                        "?q=${attraction.latitude},${attraction.longitude}($label)"
            )
            try {
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    R.string.detail_no_map_app,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupReviews() {
        updateReviewSummary()

        binding.btnSubmitReview.setOnClickListener {
            val stars = binding.ratingBarReview.rating
            if (stars <= 0f) {
                Toast.makeText(
                    requireContext(),
                    R.string.detail_review_needs_stars,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            RatingsManager.add(attractionId, stars, binding.etReviewComment.text.toString())
            binding.ratingBarReview.rating = 0f
            binding.etReviewComment.text = null
            updateReviewSummary()

            val attraction = SampleData.attractions.find { it.id == attractionId }
            if (attraction != null) {
                binding.tvDetailRating.text =
                    RatingsManager.labelFor(attraction.id, attraction.rating)
            }

            Toast.makeText(
                requireContext(),
                R.string.detail_review_thanks,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateReviewSummary() {
        val count = RatingsManager.count(attractionId)
        val average = RatingsManager.average(attractionId)
        binding.tvReviewSummary.text = if (count == 0 || average == null) {
            getString(R.string.detail_no_reviews)
        } else {
            getString(R.string.review_summary, count, "%.1f".format(average))
        }
    }

    private fun updateFavoriteIcon() {
        if (isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
