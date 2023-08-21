package com.example.primecounter.data

import com.example.primecounter.model.ISPrime
import com.example.primecounter.model.NthPrime
import retrofit2.http.GET
import retrofit2.http.Path


class PrimeCounterApi  {

    interface Service {
        @GET("nth/{number}")
        suspend fun getNthPrimeNumber(@Path("number") number: Long): NthPrime

        @GET("isprime/{number}")
        suspend fun isPrimeNumber(@Path("number") number: Long): ISPrime
    }

    companion object {
        const val API_URL = "https://www.primesfordays.com/api/primes/"
    }
}