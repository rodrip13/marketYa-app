package com.rodrip.marketya.di

import com.rodrip.marketya.BuildConfig
import com.rodrip.marketya.productList.data.remote.MarketYaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl(): String {
        return "https://raw.githubusercontent.com/rodrip13/marketYa-mock-api/main/data/v1/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }

        return builder
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideMarketYaApiService(retrofit: Retrofit): MarketYaApiService {
        return retrofit.create(MarketYaApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            //Ignora propiedades desconocidas en la respuesta JSON
            ignoreUnknownKeys = true
            //Permite formatos de JSON no estrictos, como comas finales o comillas simples
            isLenient = true
            //Permite que los valores de las propiedades desconocidas en el modelo se ignoren, y se asignen por default lo declarado en dataclass
            coerceInputValues = true
        }
    }



    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
        @Named("baseUrl") baseUrl: String
    ): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

    }
}