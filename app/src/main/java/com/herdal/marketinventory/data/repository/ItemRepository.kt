package com.herdal.marketinventory.data.repository

import com.herdal.marketinventory.data.local.Item
import com.herdal.marketinventory.data.local.ItemDao
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val itemDao: ItemDao
) {
    fun getAllItems() = itemDao.getAllItems()
    fun getItemById(id: Int) = itemDao.getItemById(id)
    suspend fun insert(item: Item) = itemDao.insert(item)
    suspend fun update(item: Item) = itemDao.update(item)
    suspend fun delete(item: Item) = itemDao.delete(item)
}