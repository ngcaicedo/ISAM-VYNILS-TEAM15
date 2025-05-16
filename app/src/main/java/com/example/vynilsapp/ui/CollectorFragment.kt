package com.example.vynilsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vynilsapp.R
import com.example.vynilsapp.databinding.FragmentCollectorBinding
import com.example.vynilsapp.ui.adapters.CollectorsAdapter
import com.example.vynilsapp.viewmodels.CollectorViewModel

class CollectorFragment : Fragment() {
    private var _binding: FragmentCollectorBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CollectorViewModel
    private var viewModelAdapter: CollectorsAdapter? = null

    companion object {
        // Bandera para indicar si es necesario refrescar los datos
        var shouldRefreshData = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectorBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = CollectorsAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        // Inicializar ViewModel
        val activity = requireActivity()
        activity.actionBar?.title = getString(R.string.app_name)

        viewModel = ViewModelProvider(this, CollectorViewModel.Factory(activity.application))[CollectorViewModel::class.java]

        // Observar cambios en el ViewModel
        viewModel.collectors.observe(viewLifecycleOwner) { collectors ->
            collectors?.let {
                viewModelAdapter!!.collectors = it
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