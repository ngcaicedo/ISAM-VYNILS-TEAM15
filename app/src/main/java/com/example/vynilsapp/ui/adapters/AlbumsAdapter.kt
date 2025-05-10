package com.example.vynilsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vynilsapp.R
import com.example.vynilsapp.databinding.AlbumItemBinding
import com.example.vynilsapp.models.Album
import com.squareup.picasso.Picasso

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>(){
    class AlbumsDiffCallback(
        private val oldList: List<Album>,
        private val newList: List<Album>
    ) : androidx.recyclerview.widget.DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].albumId == newList[newItemPosition].albumId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
    var albums :List<Album> = emptyList()
        set(value) {
            val diffCallback = AlbumsDiffCallback(field, value)
            val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    var onClick: ((Album) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)
        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.viewDataBinding.also {
            it.album = album
        }
        // Cargar la imagen con Picasso si no se carga automáticamente
        if (album.cover.isNotEmpty()) {
            Picasso.get().load(album.cover).into(holder.viewDataBinding.albumCover)
        }
        holder.itemView.setOnClickListener {
            onClick?.invoke(album)
        }
    }

    override fun getItemCount(): Int = albums.size

    class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }
} 