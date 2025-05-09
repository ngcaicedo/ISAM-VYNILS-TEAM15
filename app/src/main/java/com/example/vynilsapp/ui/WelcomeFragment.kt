package com.example.vynilsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vynilsapp.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Configuración de botones para navegar a menú

        binding.btnSeeGuestMenu.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToGuestmenuFragment("guest")
            findNavController().navigate(action)
        }

        binding.btnSeeCollectorMenu.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToGuestmenuFragment("collector")
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 