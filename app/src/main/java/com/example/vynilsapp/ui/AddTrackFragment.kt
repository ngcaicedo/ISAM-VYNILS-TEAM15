package com.example.vynilsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vynilsapp.R
import com.example.vynilsapp.databinding.FragmentAddTrackBinding
import com.example.vynilsapp.models.Album
import com.example.vynilsapp.viewmodels.AddTrackViewModel

class AddTrackFragment : Fragment() {
    private var _binding: FragmentAddTrackBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddTrackViewModel
    private var albumsList: List<Album> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            AddTrackViewModel.Factory(requireActivity().application)
        )[AddTrackViewModel::class.java]

        setupObservers()
        setupSaveButton()
    }

    private fun setupObservers() {
        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            albums?.let {
                albumsList = it
                setupAlbumsDropdown(it)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnSaveTrackAlbum.isEnabled = !isLoading
        }

        viewModel.albumUpdated.observe(viewLifecycleOwner) { album ->
            if (album != null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.track_added_success),
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            }
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                Toast.makeText(
                    requireContext(),
                    viewModel.errorMessage.value ?: getString(R.string.error_adding_track),
                    Toast.LENGTH_LONG
                ).show()
                viewModel.onNetworkErrorShown()
            }
        }
    }

    private fun setupAlbumsDropdown(albums: List<Album>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            albums.map { it.name }
        )
        binding.actvSelectAlbum.setAdapter(adapter)
    }

    private fun setupSaveButton() {
        binding.btnSaveTrackAlbum.setOnClickListener {
            if (validateFields()) {
                saveTrackToAlbum()
            } else {
                Toast.makeText(requireContext(), getString(R.string.invalid_selection), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateFields(): Boolean {
        return binding.etName.text?.isNotBlank() == true &&
                binding.etDuration.text?.isNotBlank() == true &&
                binding.actvSelectAlbum.text?.isNotBlank() == true
    }

    private fun saveTrackToAlbum() {
        val albumName = binding.actvSelectAlbum.text.toString()
        val trackName = binding.etName.text.toString()
        val trackDuration = binding.etDuration.text.toString()

        val album = albumsList.firstOrNull { it.name == albumName }
        album?.let {
            viewModel.addTrackToAlbum(
                albumId = it.albumId,
                trackName = trackName,
                trackDuration = trackDuration
            )
        } ?: run {
            Toast.makeText(requireContext(), getString(R.string.invalid_selection), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}