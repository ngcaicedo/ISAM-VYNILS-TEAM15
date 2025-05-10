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

    class PerformersDiffCallback(
        private val oldList: List<Performer>,
        private val newList: List<Performer>
    ) : androidx.recyclerview.widget.DiffUtil.Callback(){

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].performerId == newList[newItemPosition].performerId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    var performers: List<Performer> = emptyList()
        set(value) {
        val diffCallback = PerformersDiffCallback(field, value)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffCallback)
        field = value
        diffResult.dispatchUpdatesTo(this)
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