package com.orellana360.app.ui.tourism

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.orellana360.app.R
import com.orellana360.app.data.SampleData
import com.orellana360.app.databinding.FragmentTourismBinding
import com.orellana360.app.model.Attraction

class TourismFragment : Fragment() {

    private var _binding: FragmentTourismBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TourismAdapter
    private var selectedCategory: String = "Todos"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTourismBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TourismAdapter { attraction ->
            val bundle = Bundle().apply {
                putInt("attractionId", attraction.id)
            }
            findNavController().navigate(R.id.action_tourism_to_detail, bundle)
        }

        binding.rvAttractions.adapter = adapter
        adapter.submitList(SampleData.attractions)

        setupSearch()
        setupFilters()
    }

    private fun setupSearch() {
        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filterData(s?.toString() ?: "")
            }
        })
        binding.searchEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                filterData(binding.searchEdit.text.toString())
                true
            } else false
        }
    }

    private fun setupFilters() {
        val chipIds = listOf(
            R.id.chipAll, R.id.chipRivers, R.id.chipForest, R.id.chipCultural
        )

        binding.chipGroupFilter.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val chip = binding.root.findViewById<Chip>(checkedIds[0])
                selectedCategory = chip.text.toString()
                filterData(binding.searchEdit.text.toString())
            }
        }
    }

    private fun filterData(query: String) {
        val filtered = SampleData.attractions.filter { attraction ->
            val matchesQuery = query.isEmpty() ||
                    attraction.name.lowercase().contains(query.lowercase()) ||
                    attraction.description.lowercase().contains(query.lowercase())

            val matchesCategory = selectedCategory == "Todos" ||
                    attraction.category == selectedCategory ||
                    (selectedCategory == "Ríos" && attraction.category == "Río")

            matchesQuery && matchesCategory
        }
        adapter.submitList(filtered)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
