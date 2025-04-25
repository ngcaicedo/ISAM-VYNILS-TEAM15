package com.example.vynilsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Set button click listeners
        val buttonListAlbumCatalog = findViewById<AppCompatButton>(R.id.btn_see_album_catalog)
        buttonListAlbumCatalog.setOnClickListener { navigateToAlbumCatalogListActivity() }
    }

    private fun navigateToAlbumCatalogListActivity() {
        // Start ListAlbumCatalogActivity
        startActivity(Intent(this, AlbumCatalogListActivity::class.java))
    }
}