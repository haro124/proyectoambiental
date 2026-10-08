package com.orellana360.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.orellana360.app.R
import com.orellana360.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardMap.setOnClickListener {
            it.findNavController().navigate(R.id.action_home_to_map)
        }

        binding.cardTourism.setOnClickListener {
            it.findNavController().navigate(R.id.action_home_to_tourism)
        }

        binding.cardBiodiversity.setOnClickListener {
            it.findNavController().navigate(R.id.action_home_to_biodiversity)
        }


        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
