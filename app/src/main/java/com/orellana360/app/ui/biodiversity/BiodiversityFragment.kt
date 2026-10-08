package com.orellana360.app.ui.biodiversity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.orellana360.app.data.SampleData
import com.orellana360.app.databinding.FragmentBiodiversityBinding

class BiodiversityFragment : Fragment() {

    private var _binding: FragmentBiodiversityBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: BiodiversityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBiodiversityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BiodiversityAdapter()
        binding.rvSpecies.adapter = adapter
        adapter.submitList(SampleData.species)

        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s?.toString() ?: ""
                val filtered = SampleData.species.filter { species ->
                    query.isEmpty() ||
                            species.name.lowercase().contains(query.lowercase()) ||
                            species.scientificName.lowercase().contains(query.lowercase())
                }
                adapter.submitList(filtered)
            }
        })
        binding.searchEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) true else false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
