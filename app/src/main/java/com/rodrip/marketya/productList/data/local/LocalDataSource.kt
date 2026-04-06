package com.rodrip.marketya.productList.data.local

import com.rodrip.marketya.productList.data.local.database.dao.ProductDao
import com.rodrip.marketya.productList.data.local.database.dao.PromotionDao
import com.rodrip.marketya.productList.data.local.database.entity.ProductEntity
import com.rodrip.marketya.productList.data.local.database.entity.PromotionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val productDao: ProductDao,
    private val promotionDao: PromotionDao
) {

    fun getAllProducts(): Flow<List<ProductEntity>> = productDao.getAllProducts()
    suspend fun saveProducts(products: List<ProductEntity>) {
        productDao.replaceAllProducts(products)
    }


    fun getProductById(id: String): Flow<ProductEntity> = productDao.getProductById(id)

    fun getAllPromotions(): Flow<List<PromotionEntity>> = promotionDao.getAllPromotions()

    suspend fun savePromotions(promotions: List<PromotionEntity>) {
        promotionDao.replaceAll(promotions)
    }

}