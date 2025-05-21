package com.example.vynilsapp.repositories
import android.app.Application
import android.util.Log
import com.example.vynilsapp.models.Collector
import com.example.vynilsapp.network.CacheManager
import com.example.vynilsapp.network.NetworkServiceAdapter

class CollectorRepository(val application: Application) {
    suspend fun refreshData(): List<Collector> {
        // Obtener collectors desde la API
        return NetworkServiceAdapter.getInstance(application).getCollectors()
    }

    suspend fun getCollector(collectorId: Int): Collector {
        val potentialResp = CacheManager.getInstance(application.applicationContext).getCollectorDetails(collectorId)
        return if (potentialResp.collectorId == 0) {
            Log.d("Cache decision", "get from network")
            val collector = NetworkServiceAdapter.getInstance(application).getCollector(collectorId)
            CacheManager.getInstance(application.applicationContext).addCollectorDetails(collectorId, collector)
            collector
        } else {
            Log.d("Cache decision", "return elements from cache")
            potentialResp
        }
    }
} 