package com.example.primecounter.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Created by Arun Pandian  on 16/08/23.
 */
@Serializable
data class NthPrime(
    @SerialName("nth_prime")
    val nthPrime: Long
)