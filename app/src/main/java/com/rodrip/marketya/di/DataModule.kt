package com.rodrip.marketya.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.rodrip.marketya.core.coroutines.DispatchersProvider
import com.rodrip.marketya.core.data.coroutines.DefaultDispatchersProvider
import com.rodrip.marketya.productList.data.local.database.MarketYaDatabase
import com.rodrip.marketya.productList.data.local.database.dao.ProductDao
import com.rodrip.marketya.productList.data.local.database.dao.PromotionDao
import com.rodrip.marketya.productList.data.repository.ProductRepositoryImpl
import com.rodrip.marketya.productList.data.repository.PromotionRepositoryImpl
import com.rodrip.marketya.productList.domain.repository.ProductRepository
import com.rodrip.marketya.productList.domain.repository.PromotionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDispatchersProvider(
        dispatchers: DefaultDispatchersProvider
    ): DispatchersProvider {
        return dispatchers
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository {
        return productRepositoryImpl
    }

    @Provides
    @Singleton
    fun providePromotionRepository(
        promotionRepositoryImpl: PromotionRepositoryImpl
    ): PromotionRepository {
        return promotionRepositoryImpl
    }

    @Provides
    fun provideProductDao(database: MarketYaDatabase): ProductDao = database.productDao()

    @Provides
    fun providePromotionDao(database: MarketYaDatabase): PromotionDao = database.promotionDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MarketYaDatabase {
        return databaseBuilder(context, MarketYaDatabase::class.java, "market_db").build()
    }
}