package com.example.vynilsapp.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.vynilsapp.dataResponses.AlbumCatalogResponse
import com.example.vynilsapp.databinding.ItemAlbumCatalogBinding
import com.squareup.picasso.Picasso

class AlbumCatalogViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemAlbumCatalogBinding.bind(view)
    fun bind(album: AlbumCatalogResponse) {
        binding.albumName.text = album.name
        Picasso.get().load(album.cover).into(binding.albumCover)
        binding.albumCover.contentDescription = album.name
    }
}