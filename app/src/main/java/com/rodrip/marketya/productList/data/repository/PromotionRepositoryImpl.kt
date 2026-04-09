package com.rodrip.marketya.productList.data.repository

import android.util.Log
import com.rodrip.marketya.core.coroutines.DispatchersProvider
import com.rodrip.marketya.productList.data.local.LocalDataSource
import com.rodrip.marketya.productList.data.local.database.entity.PromotionEntity
import com.rodrip.marketya.productList.data.mappers.toDomain
import com.rodrip.marketya.productList.data.mappers.toEntity
import com.rodrip.marketya.productList.data.remote.RemoteDataSource
import com.rodrip.marketya.productList.domain.model.Promotion
import com.rodrip.marketya.productList.domain.repository.PromotionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PromotionRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dispatchers: DispatchersProvider,
    private val json: Json
) : PromotionRepository {

    val refreshScope = CoroutineScope(SupervisorJob() + dispatchers.io)
    val refreshMutex = Mutex()

    override fun getActivePromotions(): Flow<List<Promotion>> {
        return localDataSource.getAllPromotions()
            .map { entities ->
                entities.mapNotNull { it.toDomain(json) }
            }
            .onStart {
                refreshScope.launch {
                    if (!refreshMutex.tryLock()) return@launch

                    try {
                        refreshPromotions()
                    }
                    catch(e: Exception) {

                    } finally {
                        refreshMutex.unlock()
                    }

                }
            }
            .catch {
                // Aca va un Log importante
                Log.e("PromotionRepositoryImpl", "Error al obtener productos", it)
            }
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