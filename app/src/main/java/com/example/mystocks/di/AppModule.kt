package com.example.mystocks.di

import com.example.mystocks.data.api.AlphaVantageApi
import com.example.mystocks.data.api.FinanceApiService
import com.example.mystocks.data.api.YahooFinanceApi
import com.example.mystocks.data.repository.StockRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class YahooFinanceRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AlphaVantageRetrofit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL_YAHOO = "https://query1.finance.yahoo.com/"
    private const val BASE_URL_ALPHA_VANTAGE = "https://www.alphavantage.co/"

    @YahooFinanceRetrofit
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_YAHOO)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @AlphaVantageRetrofit
    @Provides
    @Singleton
    fun provideRetrofitForAlphaVantageApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_ALPHA_VANTAGE)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    fun provideAlphaVantageApi(@AlphaVantageRetrofit retrofit: Retrofit): AlphaVantageApi {
        return retrofit.create(AlphaVantageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideYahooFinanceApi(@YahooFinanceRetrofit retrofit: Retrofit): FinanceApiService {
        return retrofit.create(FinanceApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNiftyRepository(
        api: FinanceApiService,
        alphaVantageApi: AlphaVantageApi
    ): StockRepository {
        return StockRepository(api, alphaVantageApi)
    }
}
