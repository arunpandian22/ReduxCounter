package com.example.primecounter.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Arun Pandian  on 16/08/23.
 */
@Serializable
data class ISPrime(
    @SerialName("is_prime") val isPrime: Boolean,
    @SerialName("sequence_number") val sequenceNumber: Long? = null
)