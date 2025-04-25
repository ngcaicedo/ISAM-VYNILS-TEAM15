package com.example.vynilsapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vynilsapp.dataResponses.AlbumCatalogResponse
import com.example.vynilsapp.viewHolders.AlbumCatalogViewHolder
import com.example.vynilsapp.R

class AlbumCatalogAdapter(private var albumCatalog: List<AlbumCatalogResponse> = emptyList()) :
    RecyclerView.Adapter<AlbumCatalogViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<AlbumCatalogResponse>) {
        albumCatalog = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumCatalogViewHolder {
        return AlbumCatalogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_album_catalog, parent, false)
        )
    }

    override fun getItemCount() = albumCatalog.size

    override fun onBindViewHolder(holder: AlbumCatalogViewHolder, position: Int) {
        holder.bind(albumCatalog[position])
    }

}