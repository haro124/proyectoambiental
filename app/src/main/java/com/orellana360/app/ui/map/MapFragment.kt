package com.orellana360.app.ui.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.orellana360.app.R
import com.orellana360.app.data.SampleData
import com.orellana360.app.databinding.FragmentMapBinding
import com.orellana360.app.model.Attraction
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import java.io.File

/**
 * Mapa interactivo con los sitios turísticos geolocalizados.
 *
 * Usa osmdroid (OpenStreetMap), por lo que no necesita API key. Los tiles se descargan
 * y se cachean dentro del almacenamiento privado de la app.
 *
 * Al tocar un pin se abre la ficha del atractivo correspondiente.
 */
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Debe configurarse antes de inflar el MapView.
        val context = requireContext()
        Configuration.getInstance().apply {
            load(context, context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
            userAgentValue = context.packageName
            osmdroidBasePath = File(context.cacheDir, "osmdroid").apply { mkdirs() }
            osmdroidTileCache = File(osmdroidBasePath, "tiles").apply { mkdirs() }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            setUseDataConnection(true)
            zoomController.setVisibility(
                org.osmdroid.views.CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT
            )
        }

        val sites = SampleData.attractions.filter { it.latitude != 0.0 || it.longitude != 0.0 }
        sites.forEach { addMarker(it) }
        frameMap(sites)
    }

    private fun addMarker(attraction: Attraction) {
        val marker = Marker(binding.mapView).apply {
            position = GeoPoint(attraction.latitude, attraction.longitude)
            title = attraction.name
            subDescription = attraction.category
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            icon = androidx.core.content.ContextCompat.getDrawable(
                requireContext(), R.drawable.ic_pin
            )
            setOnMarkerClickListener { _, _ ->
                openDetail(attraction.id)
                true
            }
        }
        binding.mapView.overlays.add(marker)
    }

    /** Encuadra el mapa sobre los sitios registrados. */
    private fun frameMap(sites: List<Attraction>) {
        if (sites.isEmpty()) {
            binding.mapView.controller.setZoom(9.0)
            binding.mapView.controller.setCenter(GeoPoint(-0.4625, -76.9875))
            return
        }
        if (sites.size == 1) {
            binding.mapView.controller.setZoom(13.0)
            binding.mapView.controller.setCenter(
                GeoPoint(sites[0].latitude, sites[0].longitude)
            )
            return
        }

        val box = BoundingBox.fromGeoPoints(
            sites.map { GeoPoint(it.latitude, it.longitude) }
        )
        // El encuadre solo puede calcularse cuando el MapView ya tiene tamaño.
        binding.mapView.post {
            _binding?.mapView?.zoomToBoundingBox(box.increaseByScale(1.6f), false)
        }
    }

    private fun openDetail(attractionId: Int) {
        val args = Bundle().apply { putInt("attractionId", attractionId) }
        findNavController().navigate(R.id.action_map_to_detail, args)
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroyView() {
        binding.mapView.onDetach()
        super.onDestroyView()
        _binding = null
    }
}
