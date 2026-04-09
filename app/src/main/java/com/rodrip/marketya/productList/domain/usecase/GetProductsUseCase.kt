package com.rodrip.marketya.productList.domain.usecase

import android.util.Log
import com.rodrip.marketya.productList.domain.model.Product
import com.rodrip.marketya.productList.domain.model.ProductWithPromotion
import com.rodrip.marketya.productList.domain.repository.ProductRepository
import com.rodrip.marketya.productList.domain.repository.PromotionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.Instant
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val promotionRepository: PromotionRepository,
    private val getPromotionForProductUseCase: GetPromotionForProductUseCase
) {

    operator fun invoke(): Flow<List<ProductWithPromotion>> {
        return combine(
            productRepository.getProducts(),
            promotionRepository.getActivePromotions()
        ) { products, promotions ->
            val now = Instant.now()
            val activePromotions = promotions.filter { promotion ->
                promotion.startTime <= now && promotion.endTime >= now
            }

            products.map { product ->
                val promotion = getPromotionForProductUseCase(product, activePromotions)
                ProductWithPromotion( product = product, promotion = promotion)
            }

        }

    }
}