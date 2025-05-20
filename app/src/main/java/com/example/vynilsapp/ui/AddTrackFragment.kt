package com.example.vynilsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vynilsapp.R
import com.example.vynilsapp.databinding.FragmentAddTrackBinding
import com.example.vynilsapp.models.Album
import com.example.vynilsapp.models.Track
import com.example.vynilsapp.viewmodels.AddTrackViewModel

class AddTrackFragment : Fragment() {
    private var _binding: FragmentAddTrackBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddTrackViewModel
    private var albumsList: List<Album> = emptyList()
    private var tracksList: List<Track> = emptyList()

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
        viewModel = ViewModelProvider(this, AddTrackViewModel.Factory(requireActivity().application))[AddTrackViewModel::class.java]
        setupDropdowns()
        setupSaveButton()
        observeViewModel()
    }

    private fun setupDropdowns() {
        val dummyAlbums = listOf(
            Album(
                albumId = 1,
                name = "Sample Album",
                cover = "",
                releaseDate = "",
                description = "",
                genre = "",
                recordLabel = "",
                tracks = mutableListOf()
            )
        )

        val dummyTracks = listOf(
            Track(
                trackId = 1,
                name = "Sample Track",
                duration = "3:30"
            )
        )

        val albumAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, dummyAlbums.map { it.name })
        (binding.tilSelectAlbum.editText as? AutoCompleteTextView)?.setAdapter(albumAdapter)

        val trackAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, dummyTracks.map { it.name })
        (binding.tilTrack.editText as? AutoCompleteTextView)?.setAdapter(trackAdapter)

        albumsList = dummyAlbums
        tracksList = dummyTracks
    }

    private fun setupSaveButton() {
        binding.btnSaveTrackAlbum.setOnClickListener {
            if (validateFields()) {
                saveTrackToAlbum()
            } else {
                Toast.makeText(context, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateFields(): Boolean {
        val track = (binding.tilTrack.editText as? AutoCompleteTextView)?.text.toString().trim()
        val album = (binding.tilSelectAlbum.editText as? AutoCompleteTextView)?.text.toString().trim()
        return track.isNotEmpty() && album.isNotEmpty()
    }

    private fun saveTrackToAlbum() {
        val trackName = (binding.tilTrack.editText as? AutoCompleteTextView)?.text.toString().trim()
        val albumName = (binding.tilSelectAlbum.editText as? AutoCompleteTextView)?.text.toString().trim()

        val trackId = tracksList.first { it.name == trackName }.trackId
        val albumId = albumsList.first { it.name == albumName }.albumId

        viewModel.addTrackToAlbum(albumId, trackId)
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnSaveTrackAlbum.isEnabled = !isLoading
        }

        viewModel.albumUpdated.observe(viewLifecycleOwner) { album ->
            if (album != null) {
                Toast.makeText(context, getString(R.string.track_added_success), Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                Toast.makeText(context, viewModel.errorMessage.value ?: getString(R.string.error_adding_track), Toast.LENGTH_LONG).show()
                viewModel.onNetworkErrorShown()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}