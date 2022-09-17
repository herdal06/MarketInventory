package com.herdal.marketinventory.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.herdal.marketinventory.data.local.item.Item
import com.herdal.marketinventory.data.local.item.ItemDao

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}