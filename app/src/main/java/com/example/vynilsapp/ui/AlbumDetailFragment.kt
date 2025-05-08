package com.example.vynilsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vynilsapp.databinding.FragmentAlbumDetailBinding
import com.example.vynilsapp.viewmodels.AlbumDetailViewModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale

class AlbumDetailFragment : Fragment() {

    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumDetailViewModel
    private var shouldRefreshData = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()
        viewModel = ViewModelProvider(this, AlbumDetailViewModel.Factory(activity.application))
            .get(AlbumDetailViewModel::class.java)

        viewModel.albumDetail.observe(viewLifecycleOwner) { albumDetail ->
            albumDetail?.let {
                binding.progressCircular.visibility = View.GONE
                binding.lanzamientoAlbumDetail.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(it.releaseDate)!!)
                binding.descripcionAlbumDetail.text = it.description
                binding.selloAlbumDetail.text = it.recordLabel
                binding.generoAlbumDetail.text = it.genre
                binding.albumDetailName.text = it.name
                if (it.cover.isNotEmpty()) {
                    Picasso.get().load(it.cover).into(binding.albumDetailCover)
                }
            }
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

        val albumId = arguments?.get("albumId")?.toString() ?: return
        viewModel.getAlbumDetail(albumId)
    }

    override fun onResume() {
        super.onResume()
        if (shouldRefreshData) {
            binding.progressCircular.visibility = View.VISIBLE
            viewModel.refreshDataFromNetwork()
            shouldRefreshData = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Error de red", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}