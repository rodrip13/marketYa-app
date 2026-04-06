package com.rodrip.marketya.productList.data.repository

import android.util.Log
import com.rodrip.marketya.core.coroutines.DispatchersProvider
import com.rodrip.marketya.productList.data.local.LocalDataSource
import com.rodrip.marketya.productList.data.mappers.toEntity
import com.rodrip.marketya.productList.data.mappers.toDomain
import com.rodrip.marketya.productList.data.remote.RemoteDataSource
import com.rodrip.marketya.productList.domain.model.Product
import com.rodrip.marketya.productList.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
    val dispatchers: DispatchersProvider
) : ProductRepository {

    val refreshScope = CoroutineScope(SupervisorJob() + dispatchers.io)
    val refreshMutex = Mutex()


    override fun getProducts(): Flow<List<Product>> {
        return localDataSource.getAllProducts()
            .map { entities ->
                entities.mapNotNull { it.toDomain() }
            }
            .onStart {
                refreshScope.launch {
                    if (!refreshMutex.tryLock()) return@launch

                    try {
                        refreshProduct()
                    }
                    catch(e: Exception) {

                    } finally {
                        refreshMutex.unlock()
                    }

                }
            }
            .catch {
                // Ava va un Log importante
                Log.e("ProductRepositoryImpl", "Error al obtener productos", it)
            }
    }


    override fun getProductById(id: String): Flow<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshProduct() {
        withContext(dispatchers.io) {
            val products = remoteDataSource.getProducts().getOrThrow()
            val productsEntity = products.map { it.toEntity() }

            localDataSource.saveProducts(productsEntity)

        }
    }

}