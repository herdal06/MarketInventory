package com.herdal.marketinventory.ui.add_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.herdal.marketinventory.data.local.Item
import com.herdal.marketinventory.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemRepository.insert(item)
        }
    }

    private fun getNewItemEntry(itemName: String, itemPrice: String, quantity: String): Item {
        return Item(
            name = itemName,
            price = itemPrice.toDouble(),
            quantityInStock = quantity.toInt()
        )
    }

    fun addNewItem(itemName: String, itemPrice: String, quantity: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, quantity)
        insertItem(newItem)
    }

    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()
        ) {
            return false
        }
        return true
    }
}