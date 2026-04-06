package com.rodrip.marketya.productList.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rodrip.marketya.productList.data.local.database.dao.ProductDao
import com.rodrip.marketya.productList.data.local.database.dao.PromotionDao
import com.rodrip.marketya.productList.data.local.database.entity.ProductEntity
import com.rodrip.marketya.productList.data.local.database.entity.PromotionEntity

@Database(
    entities = [
        ProductEntity::class,
        PromotionEntity::class
        //CartEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class MarketYaDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun promotionDao(): PromotionDao
    //abstract fun cartDao(): CartDao
}