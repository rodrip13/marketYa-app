package com.rodrip.marketya.productList.domain.usecase

import android.util.Log
import com.rodrip.marketya.core.presentation.utils.roundTo2Decimals
import com.rodrip.marketya.productList.domain.model.Product
import com.rodrip.marketya.productList.domain.model.ProductPromotion
import com.rodrip.marketya.productList.domain.model.Promotion
import com.rodrip.marketya.productList.domain.model.PromotionType
import javax.inject.Inject

class GetPromotionForProductUseCase @Inject constructor() {

    operator fun invoke(product: Product, promotions: List<Promotion>): ProductPromotion? {
        val productPromo = promotions.filter { promotion ->
            promotion.productId.contains(product.id)
        }

        val percentPromos = productPromo.filter { it.type == PromotionType.PERCENT }
            .maxByOrNull {
                it.value
            }

        if (percentPromos != null) {
            val percent = percentPromos.value.coerceIn(0.0, 100.0)
            val discountedPriceCrudo = product.priceCents * (1 - percent / 100)
            val discountedPrice = product.priceCents * (1 - percent / 100).roundTo2Decimals()

            Log.d("Promotion", "Rodrip cuenta: ${product.priceCents} X (1 - ${percent}% / 100) = esto deberia dar: $discountedPriceCrudo pero esta dando $discountedPrice")
            return ProductPromotion.Percent(percent, discountedPrice)
        }

        val buyPayPromos = productPromo.firstOrNull() { it.type == PromotionType.BUY_X_PAY_Y }
        if (buyPayPromos != null) {
            val buyQuantity = buyPayPromos.buyQuantity ?: return null
            val payQuantity = buyPayPromos.value.toInt().coerceIn(0, buyQuantity)

            return ProductPromotion.BuyXPayY(
                buyQuantity = buyQuantity,
                payQuantity = payQuantity,
                label = "${buyQuantity}X${payQuantity}" // compra X paga Y (3x2)
            )
        }

        return null
    }
}