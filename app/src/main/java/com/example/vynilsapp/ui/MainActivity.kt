package com.example.vynilsapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vynilsapp.R

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
        
        // Configurar el listener del botón para mostrar el catálogo de álbumes
        val btnSeeAlbumCatalog = findViewById<AppCompatButton>(R.id.btn_see_album_catalog)
        btnSeeAlbumCatalog.setOnClickListener {
            // Ocultar elementos de bienvenida
            findViewById<CardView>(R.id.welcomeView).visibility = View.GONE
            btnSeeAlbumCatalog.visibility = View.GONE
            
            // Mostrar el fragment_container
            findViewById<View>(R.id.fragment_container).visibility = View.VISIBLE
            
            // Cargar el Fragment
            loadAlbumFragment()
        }
    }
    
    private fun loadAlbumFragment() {
        val albumFragment = AlbumFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, albumFragment)
            .commit()
    }
}