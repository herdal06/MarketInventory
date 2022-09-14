package com.herdal.marketinventory.ui

import androidx.lifecycle.ViewModel
import com.herdal.marketinventory.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {
}