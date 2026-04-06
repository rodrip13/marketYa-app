package com.rodrip.marketya.productList.data.repository

import com.rodrip.marketya.core.coroutines.DispatchersProvider
import com.rodrip.marketya.productList.data.local.LocalDataSource
import com.rodrip.marketya.productList.data.local.database.entity.PromotionEntity
import com.rodrip.marketya.productList.data.mappers.toEntity
import com.rodrip.marketya.productList.data.remote.RemoteDataSource
import com.rodrip.marketya.productList.domain.model.Promotion
import com.rodrip.marketya.productList.domain.repository.PromotionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PromotionRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dispatchers: DispatchersProvider,
    private val json: Json
) : PromotionRepository {

    override fun getActivePromotions(): Flow<List<Promotion>> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshPromotions() {
        withContext(dispatchers.io) {
            try {
                val promotions = remoteDataSource.getPromotions().getOrThrow()
                val promotionsEntity: List<PromotionEntity> = promotions.mapNotNull { it.toEntity(json) }
                localDataSource.savePromotions(promotionsEntity)
            } catch (e: Exception) {
                // Log importante
            }
        }

    }
}