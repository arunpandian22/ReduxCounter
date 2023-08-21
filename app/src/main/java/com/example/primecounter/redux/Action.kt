package com.example.primecounter.redux

import com.example.primecounter.model.Activity
import com.example.primecounter.model.ISPrime


/**
 * Created by Arun Pandian  on 18/08/23.
 */



class VerifyFavorites(val prime: Long) : Action
object LoadingIsPrimeAPI : Action
class IsPrimeAPIError(val error: String) : Action
object ClearPrimeMessage : Action
data class FavouritePrime(val prime: Long) : Action
data class UnFavouritePrime(val prime: Long) : Action


data class AddActivity(val activity: Activity) : Action


object Increment : Action
object Decrement : Action



