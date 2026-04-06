package com.rodrip.marketya.productList.data.mappers

import com.rodrip.marketya.productList.data.local.database.entity.ProductEntity
import com.rodrip.marketya.productList.data.remote.response.ProductResponse
import com.rodrip.marketya.productList.domain.model.Product

fun ProductResponse.toEntity(): ProductEntity {
    val price = priceCents?.div(100.0) ?: 0.0

    return ProductEntity(
        id = id,
        name = name,
        description = description,
        category = category,
        price = price,
        stock = stock,
        imageUrl = imageUrl
    )
}

fun ProductEntity.toDomain(): Product? {
    if(category.isNullOrEmpty()) return null

    return Product(
        id = id,
        name = name,
        description = description ?: "no description",
        category = category,
        priceCents = price,
        stock = stock ?: 0,
        imageUrl = imageUrl ?: ""
    )
}