package com.herdal.marketinventory.ui.item_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.herdal.marketinventory.data.local.Item
import com.herdal.marketinventory.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val repository: ItemRepository
) : ViewModel() {

    fun retrieveItem(id: Int): LiveData<Item> {
        return repository.getItemById(id).asLiveData()
    }
}