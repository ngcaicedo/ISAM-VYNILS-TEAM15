package com.example.vynilsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vynilsapp.R
import com.example.vynilsapp.databinding.FragmentAlbumBinding
import com.example.vynilsapp.models.Album
import com.example.vynilsapp.ui.adapters.AlbumsAdapter
import com.example.vynilsapp.viewmodels.AlbumViewModel

class AlbumFragment : Fragment() {
    private var _binding: FragmentAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null

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
        
        // Configurar botón de creación de álbum
        binding.btnCreateAlbum.setOnClickListener {
            // Aquí se puede navegar a un nuevo fragmento para crear álbum
            // Por ahora, sólo mostraremos un mensaje
            Toast.makeText(context, "Crear nuevo álbum", Toast.LENGTH_SHORT).show()
            // TODO: Implementar navegación a pantalla de creación de álbum
        }
        
        // Inicializar ViewModel
        val activity = requireActivity()
        activity.actionBar?.title = getString(R.string.app_name)
        
        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(activity.application))
            .get(AlbumViewModel::class.java)
        
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
    
    // Método para navegar a la pantalla de creación de álbum
    private fun navigateToCreateAlbum() {
        // TODO: Implementar cuando se cree el fragment de creación de álbum
        // val action = AlbumFragmentDirections.actionAlbumFragmentToCreateAlbumFragment()
        // findNavController().navigate(action)
    }
} 