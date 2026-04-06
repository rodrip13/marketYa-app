package com.rodrip.marketya.productList.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String?,
    val category: String?,
    val price: Double,
    val stock: Int?,
    val imageUrl: String?

)