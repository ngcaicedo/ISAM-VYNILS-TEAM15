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
import com.example.vynilsapp.databinding.FragmentPerformerBinding
import com.example.vynilsapp.ui.adapters.PerformersAdapter
import com.example.vynilsapp.viewmodels.PerformerViewModel

class PerformerFragment : Fragment() {
    private var _binding: FragmentPerformerBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: PerformerViewModel
    private var viewModelAdapter: PerformersAdapter? = null

    companion object {
        // Bandera para indicar si es necesario refrescar los datos
        var shouldRefreshData = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerformerBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = PerformersAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 2) // 2 columnas
        recyclerView.adapter = viewModelAdapter

        // Click on cover and navigate to detail
        viewModelAdapter!!.onClick = { performer ->
            val typePerformer = if (performer.birthDate == null) "Band" else "Musician"
            Log.i("PerformerFragment", "PerformerFragment - typePerformer: $typePerformer | performerId: ${performer.performerId}")
            val action = PerformerFragmentDirections.actionPerformerFragmentToPerformerDetailFragment(performer.performerId, typePerformer)
            findNavController().navigate(action)
        }

        // Inicializar ViewModel
        val activity = requireActivity()
        activity.actionBar?.title = getString(R.string.app_name)

        viewModel = ViewModelProvider(this, PerformerViewModel.Factory(activity.application))[PerformerViewModel::class.java]

        // Observar cambios en el ViewModel
        viewModel.performers.observe(viewLifecycleOwner) { performers ->
            performers?.let {
                viewModelAdapter!!.performers = it
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