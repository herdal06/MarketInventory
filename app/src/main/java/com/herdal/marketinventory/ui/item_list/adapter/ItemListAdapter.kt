package com.herdal.marketinventory.ui.item_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.herdal.marketinventory.data.local.Item
import com.herdal.marketinventory.databinding.ListItemBinding
import com.herdal.marketinventory.utils.extensions.getFormattedPrice

class ItemListAdapter(private val onItemClicked: (id: Int) -> Unit) :
    ListAdapter<Item, ItemListAdapter.ItemViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem == newItem
        }
    }

    class ItemViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) = binding.apply {
            tvItemName.text = item.name
            tvItemPrice.text = item.getFormattedPrice()
            tvItemQuantity.text = item.quantityInStock.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(currentItem.id)
        }
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            if (currentItem != null) {
                onItemClicked.invoke(currentItem.id)
            }
        }
    }
}