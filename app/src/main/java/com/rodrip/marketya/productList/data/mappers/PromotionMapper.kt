package com.rodrip.marketya.productList.data.mappers

import com.rodrip.marketya.productList.data.local.database.entity.PromotionEntity
import com.rodrip.marketya.productList.data.remote.response.PromotionResponse
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

fun PromotionResponse.toEntity(json: Json): PromotionEntity?{

    if (startAtEpoch == null || endAtEpoch == null) return null

    val productIds: List<String> = listOf(productId)
    val productIdsJson: String = json.encodeToString(
        ListSerializer(String.serializer()),
        productIds)

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