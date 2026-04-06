package com.rodrip.marketya.productList.domain.repository

import com.rodrip.marketya.productList.domain.model.Promotion
import kotlinx.coroutines.flow.Flow

interface PromotionRepository {
    fun getActivePromotions(): Flow<List<Promotion>>
    suspend fun refreshPromotions()
}