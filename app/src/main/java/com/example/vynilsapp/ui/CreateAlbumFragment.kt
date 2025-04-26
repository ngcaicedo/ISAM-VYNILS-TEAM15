package com.example.vynilsapp.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.vynilsapp.R
import com.example.vynilsapp.databinding.FragmentCreateAlbumBinding
import com.example.vynilsapp.viewmodels.CreateAlbumViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateAlbumFragment : Fragment() {
    private var _binding: FragmentCreateAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CreateAlbumViewModel
    private val calendar = Calendar.getInstance()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(this, CreateAlbumViewModel.Factory(requireActivity().application))
            .get(CreateAlbumViewModel::class.java)
        
        setupDatePicker()
        setupSaveButton()
        observeViewModel()
    }
    
    private fun setupDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
        
        binding.etReleaseDate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
    
    private fun updateDateInView() {
        val format = "yyyy-MM-dd'T'HH:mm:ss-05:00"
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        binding.etReleaseDate.setText(sdf.format(calendar.time))
    }
    
    private fun setupSaveButton() {
        binding.btnSaveAlbum.setOnClickListener {
            if (validateFields()) {
                createAlbum()
            } else {
                Toast.makeText(context, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun validateFields(): Boolean {
        val name = binding.etName.text.toString().trim()
        val cover = binding.etCover.text.toString().trim()
        val releaseDate = binding.etReleaseDate.text.toString().trim()
        val description = binding.etDescription.text.toString().trim()
        val genre = binding.etGenre.text.toString().trim()
        val recordLabel = binding.etRecordLabel.text.toString().trim()
        
        return name.isNotEmpty() && cover.isNotEmpty() && releaseDate.isNotEmpty() &&
                description.isNotEmpty() && genre.isNotEmpty() && recordLabel.isNotEmpty()
    }
    
    private fun createAlbum() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSaveAlbum.isEnabled = false
        
        viewModel.createAlbum(
            name = binding.etName.text.toString().trim(),
            cover = binding.etCover.text.toString().trim(),
            releaseDate = binding.etReleaseDate.text.toString().trim(),
            description = binding.etDescription.text.toString().trim(),
            genre = binding.etGenre.text.toString().trim(),
            recordLabel = binding.etRecordLabel.text.toString().trim()
        )
    }
    
    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnSaveAlbum.isEnabled = !isLoading
        }
        
        viewModel.albumCreated.observe(viewLifecycleOwner) { album ->
            if (album != null) {
                Toast.makeText(context, getString(R.string.album_created_success), Toast.LENGTH_SHORT).show()
                // Navegar de vuelta a la lista de álbumes
                findNavController().popBackStack()
            }
        }
        
        viewModel.eventNetworkError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                Toast.makeText(context, viewModel.errorMessage.value ?: getString(R.string.error_creating_album), Toast.LENGTH_LONG).show()
                viewModel.onNetworkErrorShown()
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 