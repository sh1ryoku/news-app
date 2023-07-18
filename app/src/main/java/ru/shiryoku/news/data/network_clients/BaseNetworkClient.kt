package ru.shiryoku.news.data.network_clients

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.shiryoku.news.BuildConfig

class BaseNetworkClient {

    private val apiKeyInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("apiKey", BuildConfig.API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder().url(newUrl).build()

        chain.proceed(newRequest)
    }

    private val httpClient = OkHttpClient.Builder().addInterceptor(apiKeyInterceptor).build()

    private val retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.API_BASE_URL).client(httpClient).addConverterFactory(
            GsonConverterFactory.create()
        ).build()

    fun <T> create(baseClass: Class<T>): T {
        return retrofit.create(baseClass)
    }
}