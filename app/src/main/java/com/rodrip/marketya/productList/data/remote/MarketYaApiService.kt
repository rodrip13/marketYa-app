package com.rodrip.marketya.productList.data.remote

import com.rodrip.marketya.productList.data.remote.response.ProductsResponse
import com.rodrip.marketya.productList.data.remote.response.PromotionsResponse
import retrofit2.http.GET

interface MarketYaApiService {

    // data/v1/products
    @GET("products.json")
    suspend fun getProducts(): ProductsResponse

    // data/v1/promotions
    @GET("promotions.json")
    suspend fun getPromotions(): PromotionsResponse


}