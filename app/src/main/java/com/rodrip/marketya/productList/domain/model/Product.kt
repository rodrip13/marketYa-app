package com.rodrip.marketya.productList.domain.model


data class Product(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val priceCents: Double,
    val stock: Int,
    val imageUrl: String,
)