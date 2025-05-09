package com.example.vynilsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vynilsapp.databinding.FragmentPerformerDetailBinding
import com.example.vynilsapp.viewmodels.PerformerDetailViewModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale

class PerformerDetailFragment : Fragment() {

    private var _binding: FragmentPerformerDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PerformerDetailViewModel
    private var shouldRefreshData = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerformerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity()
        viewModel = ViewModelProvider(this, PerformerDetailViewModel.Factory(activity.application))[PerformerDetailViewModel::class.java]

        // Validar tipo de performer para mostrar fecha de nacimiento o fecha de creación
        val args = PerformerDetailFragmentArgs.fromBundle(requireArguments())
        val type = args.typePerformer

        if (type == "Band") {
            binding.fechaNacimientoPerformerDetailLabel.visibility = View.GONE
            binding.fechaNacimientoPerformerDetail.visibility = View.GONE
        } else {
            binding.fechaCreacionPerformerDetailLabel.visibility = View.GONE
            binding.fechaCreacionPerformerDetail.visibility = View.GONE
        }

        // Envío de datos al fragmento de performer detail
        viewModel.performerDetail.observe(viewLifecycleOwner) { performerDetail ->
            performerDetail?.let {
                binding.progressCircular.visibility = View.GONE
                binding.nombrePerformerDetail.text = it.name
                binding.fechaNacimientoPerformerDetail.text = it.birthDate?.let { birthDate ->
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(birthDate)!!)
                } ?: "N/A"
                binding.fechaCreacionPerformerDetail.text = it.creationDate?.let { creationDate ->
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(creationDate)!!)
                } ?: "N/A"
                binding.descripcionPerformerDetail.text = it.description
                if (it.image.isNotEmpty()) {
                    Picasso.get().load(it.image).into(binding.performerDetailPhoto)
                }
            }
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

        val performerId = arguments?.getInt("performerId")?.toString() ?: return
        val typePerformer = arguments?.getString("typePerformer") ?: return
        Log.i("PerformerFragment", "PerformerDetailFragment - typePerformer: ${typePerformer} | performerId: ${performerId}")
        viewModel.getPerformerDetail(performerId, typePerformer)
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