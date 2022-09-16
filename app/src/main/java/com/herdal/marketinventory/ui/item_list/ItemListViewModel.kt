package com.herdal.marketinventory.ui.item_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.herdal.marketinventory.data.local.Item
import com.herdal.marketinventory.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    val allItems: LiveData<List<Item>> = itemRepository.getAllItems().asLiveData()

    private val itemsEventChannel = Channel<ItemsEvent>()
    val itemsEvent = itemsEventChannel.receiveAsFlow()

    fun deleteAllItems() {
        viewModelScope.launch {
            itemRepository.deleteAll()
        }
    }

    fun onItemSwiped(item: Item) = viewModelScope.launch {
        itemRepository.delete(item)
        itemsEventChannel.send(ItemsEvent.ShowUndoDeleteItemMessage(item))
    }

    fun onUndoDeleteClick(item: Item) = viewModelScope.launch {
        itemRepository.insert(item)
    }

    sealed class ItemsEvent {
        data class ShowUndoDeleteItemMessage(val item: Item) : ItemsEvent()
    }

    fun searchByName(searchQuery: String): LiveData<List<Item>> =
        itemRepository.searchByName(searchQuery).asLiveData()
}