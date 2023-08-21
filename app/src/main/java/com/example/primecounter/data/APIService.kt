package com.example.primecounter.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit


/**
 * Created by Arun Pandian  on 20/08/23.
 */
class APIService {


    companion object {
        val instance by lazy {
            APIService()
        }
    }


    fun getAPIService(): PrimeCounterApi.Service {
        val retrofit = getRetrofit(getOkHttpClient())
        return retrofit.create(PrimeCounterApi.Service::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .build()
    }


    private fun getRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(getKotlinxSerialisationConverterFactoryNew())
            .baseUrl(PrimeCounterApi.API_URL)
            .build()
    }

    private fun getKotlinxSerialisationConverterFactoryNew(): Converter.Factory {
        val contentType = "application/json; charset=utf-8".toMediaType()
        val json = Json {
            coerceInputValues = true; ignoreUnknownKeys = true; prettyPrint = true; encodeDefaults =
            true; explicitNulls = false
        }

        return json.asConverterFactory(contentType)
    }
}