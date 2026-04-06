package com.rodrip.marketya.productList.domain.repository

import com.rodrip.marketya.productList.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(): Flow<List<Product>>
    fun getProductById(id: String): Flow<Product>

    suspend fun refreshProduct()

}