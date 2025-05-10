package com.example.vynilsapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vynilsapp.R
import com.example.vynilsapp.databinding.PerformerItemBinding
import com.example.vynilsapp.models.Performer
import com.squareup.picasso.Picasso

class PerformersAdapter : RecyclerView.Adapter<PerformersAdapter.PerformerViewHolder>(){

    var performers :List<Performer> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClick: ((Performer) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformerViewHolder {
        val withDataBinding: PerformerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            PerformerViewHolder.LAYOUT,
            parent,
            false)
        return PerformerViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: PerformerViewHolder, position: Int) {
        val performer = performers[position]
        holder.viewDataBinding.also {
            it.performer = performer
        }
        // Cargar la imagen con Picasso si no se carga automáticamente
        if (performer.image.isNotEmpty()) {
            Picasso.get().load(performer.image).into(holder.viewDataBinding.performerImage)
        }
        holder.itemView.setOnClickListener {
            onClick?.invoke(performer)
        }
    }

    override fun getItemCount(): Int {
        return performers.size
    }

    class PerformerViewHolder(val viewDataBinding: PerformerItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.performer_item
        }
    }
}