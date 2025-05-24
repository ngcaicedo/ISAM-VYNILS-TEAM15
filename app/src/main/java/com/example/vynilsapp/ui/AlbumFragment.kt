package com.example.vynilsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vynilsapp.R
import com.example.vynilsapp.databinding.FragmentAlbumBinding
import com.example.vynilsapp.ui.adapters.AlbumsAdapter
import com.example.vynilsapp.viewmodels.AlbumViewModel

class AlbumFragment : Fragment() {
    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null

    companion object {
        // Bandera para indicar si es necesario refrescar los datos
        var shouldRefreshData = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumsAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Configurar RecyclerView
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2) // 2 columnas
        recyclerView.adapter = viewModelAdapter

        // Validar tipo de usuario para mostrar botón de creación de álbum
        val args = AlbumFragmentArgs.fromBundle(requireArguments())
        val typeUser = args.typeUser

        if (typeUser != "collector") {
            binding.btnCreateAlbum.visibility = View.GONE
            binding.btnAddTrack.visibility = View.GONE
        } else {
            binding.btnCreateAlbum.visibility = View.VISIBLE
            binding.btnAddTrack.visibility = View.VISIBLE
            // Configurar botón de creación de álbum
            binding.btnCreateAlbum.setOnClickListener {
                // Navegar al fragmento de creación de álbum
                findNavController().navigate(R.id.action_albumFragment_to_createAlbumFragment)
            }
            binding.btnAddTrack.setOnClickListener{
                findNavController().navigate(R.id.action_albumFragment_to_addTrackFragment)
            }
        }

        // Click on cover and navigate to detail
        viewModelAdapter!!.onClick = { album ->
            Log.i("AlbumFragment", "Album clicked: ${album.name}")
           val action = AlbumFragmentDirections.actionAlbumFragmentToAlbumDetailFragment(album.albumId)
           findNavController().navigate(action)
        }
        
        // Inicializar ViewModel
        val activity = requireActivity()
        activity.actionBar?.title = getString(R.string.app_name)

        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(activity.application))[AlbumViewModel::class.java]
        
        // Observar cambios en el ViewModel
        viewModel.albums.observe(viewLifecycleOwner) { albums ->
            albums?.let {
                viewModelAdapter!!.albums = it
                binding.progressCircular.visibility = View.GONE
            }
        }
        
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }
    
    // Modificar método onResume para refrescar solo cuando sea necesario
    override fun onResume() {
        super.onResume()
        // Solo refrescar si la bandera está activada
        if (shouldRefreshData) {
            // Mostrar el indicador de carga
            binding.progressCircular.visibility = View.VISIBLE
            // Refrescar datos desde la API
            viewModel.refreshDataFromNetwork()
            // Restablecer la bandera
            shouldRefreshData = false
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Error de red", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
} 