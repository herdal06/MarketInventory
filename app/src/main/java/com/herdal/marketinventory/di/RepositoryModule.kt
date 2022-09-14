package com.herdal.marketinventory.di

import com.herdal.marketinventory.data.local.ItemDao
import com.herdal.marketinventory.data.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    fun provideItemRepository(
        itemDao: ItemDao
    ): ItemRepository = ItemRepository(itemDao)
}