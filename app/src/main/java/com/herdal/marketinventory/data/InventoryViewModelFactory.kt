package com.herdal.marketinventory.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.herdal.marketinventory.data.repository.ItemRepository
import com.herdal.marketinventory.ui.InventoryViewModel

class InventoryViewModelFactory(
    private val itemRepository: ItemRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}