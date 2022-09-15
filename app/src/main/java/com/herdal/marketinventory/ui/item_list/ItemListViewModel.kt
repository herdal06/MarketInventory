package com.herdal.marketinventory.ui.item_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.herdal.marketinventory.data.local.Item
import com.herdal.marketinventory.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel @Inject constructor(
    itemRepository: ItemRepository
) : ViewModel() {

    val allItems: LiveData<List<Item>> = itemRepository.getAllItems().asLiveData()
}