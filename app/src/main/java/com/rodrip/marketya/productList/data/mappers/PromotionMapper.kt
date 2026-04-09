package com.rodrip.marketya.productList.data.mappers

import com.rodrip.marketya.productList.data.local.database.entity.PromotionEntity
import com.rodrip.marketya.productList.data.remote.response.PromotionResponse
import com.rodrip.marketya.productList.domain.model.Promotion
import com.rodrip.marketya.productList.domain.model.PromotionType
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import java.time.Instant

fun PromotionEntity.toDomain(json: Json): Promotion? {

    val decodeProductIds = runCatching {
        json.decodeFromString(
            ListSerializer(String.serializer()),
            productIds
        )
    }.getOrNull()

    val finalType = runCatching {
        PromotionType.valueOf(type.trim().uppercase())
    }.getOrNull()

    if (decodeProductIds == null || finalType == null) return null

    val finalOfferValue= when(finalType){
        PromotionType.PERCENT -> {
            percent
        }
        PromotionType.BUY_X_PAY_Y -> {
            payY
        }
    }?.toDouble()

    if (finalOfferValue == null) return null

    return Promotion(
        id = id,
        type = finalType,
        productId = decodeProductIds,
        value = finalOfferValue,
        buyQuantity = buyX,
        startTime = Instant.ofEpochSecond(startAtEpoch),
        endTime = Instant.ofEpochSecond(endAtEpoch)
    )
}

fun PromotionResponse.toEntity(json: Json): PromotionEntity? {

    if (startAtEpoch == null || endAtEpoch == null) return null

    val productIds: List<String> = listOf(productId)
    val productIdsJson: String = json.encodeToString(
        ListSerializer(String.serializer()),
        productIds
    )

    return PromotionEntity(
        id = id,
        productIds = productIdsJson,
        type = type,
        percent = percent,
        buyX = buyX,
        payY = payY,
        startAtEpoch = startAtEpoch,
        endAtEpoch = endAtEpoch
    )

}