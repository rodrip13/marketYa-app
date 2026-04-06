package com.rodrip.marketya.productList.data.remote

import com.rodrip.marketya.core.domain.model.AppError
import com.rodrip.marketya.productList.data.remote.response.ProductResponse
import com.rodrip.marketya.productList.data.remote.response.PromotionResponse
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val marketYaApiService: MarketYaApiService) {

     suspend fun getProducts(): Result<List<ProductResponse>> {
          return try {
               val response = marketYaApiService.getProducts()
               Result.success(response.products)
          } catch (e: Exception) {
               Result.failure(mapToDomainError(e))
          }
     }

    suspend fun getPromotions(): Result<List<PromotionResponse>> {
        return try {
            val response = marketYaApiService.getPromotions()
            Result.success(response.promotions)
        } catch (e: Exception) {
            Result.failure(mapToDomainError(e))
        }
    }

    private fun mapToDomainError(e: Exception): AppError {
        // Aquí puedes mapear diferentes tipos de excepciones a tus errores de dominio personalizados
        return when (e) {
            is UnknownHostException -> AppError.NetworkError
            is SocketTimeoutException -> AppError.NetworkError
            is IOException -> AppError.NetworkError
            is HttpException -> {
                when (e.code()) {
                    404 -> AppError.NotFoundError
                    else -> AppError.NetworkError
                }
            }
            else -> AppError.UnknownError(e.message)

        }

    }
}