package com.rodrip.marketya.productList.domain.usecase

import android.util.Log
import com.rodrip.marketya.productList.domain.model.Product
import com.rodrip.marketya.productList.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    operator fun invoke(): Flow<List<Product>> {
        return productRepository.getProducts()
    }
}