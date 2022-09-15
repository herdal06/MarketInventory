package com.herdal.marketinventory.ui.item_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.herdal.marketinventory.data.local.Item
import com.herdal.marketinventory.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    val allItems: LiveData<List<Item>> = itemRepository.getAllItems().asLiveData()

    fun deleteAllItems() {
        viewModelScope.launch {
            itemRepository.deleteAll()
        }
    }
}