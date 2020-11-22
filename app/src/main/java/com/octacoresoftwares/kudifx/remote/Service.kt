package com.octacoresoftwares.kudifx.remote

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val BASE_URL = "http://data.fixer.io/api/"

object Service {

    @JvmStatic
    fun createService(): NetworkApi
            = getRetrofitService().create(NetworkApi::class.java)

    fun createService(baseUrl: HttpUrl): NetworkApi
            = getRetrofitService(baseUrl).create(NetworkApi::class.java)

    @JvmStatic
    private fun getRetrofitService() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getClient())
            .build()

    private fun getRetrofitService(baseUrl: HttpUrl) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .build()

    @JvmStatic
    private fun getClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }
}

interface NetworkApi {
    @GET("/latest")
    suspend fun getLatestRate(@Query("access_key") key: String): LatestRates
}