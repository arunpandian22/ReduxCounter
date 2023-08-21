package com.example.primecounter.redux

import com.example.primecounter.model.Activity
import com.example.primecounter.model.FavouriteInfoMessage


/**
 * Created by Arun Pandian  on 18/08/23.
 */
data class AppState(
    val favouriteState: FavouriteState = FavouriteState(emptyList()),
    val counterState: CounterState = CounterState(),
    val activityState: ActivityState = ActivityState(
        emptyList()
    ),
)

data class ActivityState(
    val activities: List<Activity>,
)

data class FavouriteState(
    val favourites: List<Long>,
    val isLoading: Boolean = false,
    val infoMessage: FavouriteInfoMessage? = null,
)

fun FavouriteState.isAlreadyFavorite(no: Long): Boolean {
    return favourites.contains(no)
}

data class CounterState(val count: Long = 0L)

fun <T> List<T>.add(t: T): List<T> {
    val mutable = toMutableList()
    mutable.add(t)
    return mutable
}

fun <T> List<T>.remove(t: T): List<T> {
    val mutable = toMutableList()
    mutable.remove(t)
    return mutable
}

class CounterAppStore : AppStore<AppState>(
    initialState = AppState(),
    reducers = listOf(::reduceFavouriteState, ::reduceCounterState, ::reduceActivityState),
    middleware = listOf(::loggerMiddleware, ::isPrimeMiddleWare)
) {
    companion object {
        val instance by lazy {
            CounterAppStore()
        }
    }

}


