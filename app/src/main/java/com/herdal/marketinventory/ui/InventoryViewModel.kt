package com.herdal.marketinventory.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.marketinventory.data.local.Item
import com.herdal.marketinventory.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemRepository.insert(item)
        }
    }

    private fun getNewItemEntry(itemName: String, itemPrice: Double, quantity: Int): Item {
        return Item(
            name = itemName,
            price = itemPrice,
            quantityInStock = quantity
        )
    }

    fun addNewItem(itemName: String, itemPrice: Double, quantity: Int) {
        val newItem = getNewItemEntry(itemName, itemPrice, quantity)
        insertItem(newItem)
    }
}