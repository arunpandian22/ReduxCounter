package com.example.primecounter.model

import androidx.annotation.StringDef
import com.example.primecounter.model.Actions.Companion.FAVOURITE
import com.example.primecounter.model.Actions.Companion.UN_FAVOURITE


/**
 * Created by Arun Pandian  on 16/08/23.
 */
data class Activity(val primeNumber: Long, val action: Action)

@Retention(AnnotationRetention.SOURCE)
@StringDef(FAVOURITE, UN_FAVOURITE)
annotation class Actions {
    companion object {
        const val FAVOURITE = "favourited ❤️"
        const val UN_FAVOURITE = "Unfavourited \uD83D\uDE41"
    }
}

sealed class FavouriteInfoMessage(val message: String)
object AlreadyExist : FavouriteInfoMessage("")
class Error(error: String) : FavouriteInfoMessage(error)
object Success : FavouriteInfoMessage("")

sealed class Action(@Actions val action: String)
object Favourite : Action(FAVOURITE)
object UnFavourite : Action(UN_FAVOURITE)