package com.herdal.marketinventory.di

import android.content.Context
import androidx.room.Room
import com.herdal.marketinventory.data.local.ItemDao
import com.herdal.marketinventory.data.local.ItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideItemDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ItemDatabase::class.java,
        "market_inventory_db"
    ).build()

    @Singleton
    @Provides
    fun provideItemDao(
        db: ItemDatabase
    ): ItemDao = db.itemDao()
}