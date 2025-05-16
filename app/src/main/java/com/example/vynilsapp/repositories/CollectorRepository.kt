package com.example.vynilsapp.repositories
import android.app.Application
import com.example.vynilsapp.models.Collector
import com.example.vynilsapp.network.NetworkServiceAdapter

class CollectorRepository(val application: Application) {
    suspend fun refreshData(): List<Collector> {
        // Obtener collectors desde la API
        return NetworkServiceAdapter.getInstance(application).getCollectors()
    }
} 