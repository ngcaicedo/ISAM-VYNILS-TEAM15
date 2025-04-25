package com.example.vynilsapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vynilsapp.adapters.AlbumCatalogAdapter
import com.example.vynilsapp.api.ApiService
import com.example.vynilsapp.databinding.ActivityAlbumCatalogListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlbumCatalogListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlbumCatalogListBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: AlbumCatalogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAlbumCatalogListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Retrofit
        retrofit = getRetrofitInstance()
        initUI()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initUI() {
        Log.i("AlbumCatalogList", "UI initialized")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = retrofit.create(ApiService::class.java)
                val response = apiService.getAlbumCatalog()
                if (response.isSuccessful) {
                    val albumCatalog = response.body()
                    Log.i("AlbumCatalogList", "Album Catalog: $albumCatalog")
                    runOnUiThread{
                        adapter.updateList(response.body() ?: emptyList())
                        binding.progressCircular.isVisible = false
                    }
                } else {
                    Log.e("AlbumCatalogList", "Error fetching album catalog: ${response.errorBody()}")
                    runOnUiThread {
                        showError("Error al obtener el catálogo de álbumes")
                    }
                }
            } catch (e: Exception) {
                Log.e("AlbumCatalogList", "Exception: ${e.message}", e)
                runOnUiThread {
                    showError("Ocurrió un error inesperado")
                }
            }
        }
        adapter = AlbumCatalogAdapter()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://backvynils-q6yc.onrender.com/") // Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}