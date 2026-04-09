package com.rodrip.marketya.productList.domain.model

data class ProductWithPromotion(
    val product: Product,
    val promotion: ProductPromotion? = null
)

sealed interface ProductPromotion {
    data class Percent(
        val percent: Double,
        val discountedPrice: Double
    ): ProductPromotion

    data class BuyXPayY(
        val buyQuantity: Int,
        val payQuantity: Int,
        val label: String
    ): ProductPromotion
}
