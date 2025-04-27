package com.example.vynilsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vynilsapp.R
import com.example.vynilsapp.databinding.FragmentGuestMenuBinding

class GuestMenuFragment : Fragment() {
    private var _binding: FragmentGuestMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGuestMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = GuestMenuFragmentArgs.fromBundle(requireArguments())
        val typeUser = args.typeUser

        binding.btnSeeAlbumCatalog.setOnClickListener {
            val action = GuestMenuFragmentDirections.actionGuestMenuFragmentToAlbumFragment(typeUser)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}