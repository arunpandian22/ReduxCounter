package com.example.primecounter.redux

import com.example.primecounter.model.Error
import com.example.primecounter.model.Success


/**
 * Created by Arun Pandian  on 21/08/23.
 */

fun reduceCounterState(state: AppState, action: Action): AppState {
    return when (action) {
        is Increment -> state.copy(
            counterState = CounterState(
                state.counterState.count.inc()
            )
        )

        is Decrement -> state.copy(
            counterState = CounterState(
                state.counterState.count.dec()
            )
        )

        else -> state
    }
}

fun reduceActivityState(state: AppState, action: Action): AppState {
    return when (action) {
        is AddActivity -> state.copy(
            activityState = ActivityState(
                state.activityState.activities.add(action.activity)
            )
        )

        else -> state
    }
}

fun reduceFavouriteState(state: AppState, action: Action): AppState {
    return when (action) {

        is LoadingIsPrimeAPI -> state.copy(
            favouriteState = state.favouriteState.copy(isLoading = true, infoMessage = null)
        )

        is ClearPrimeMessage -> state.copy(
            favouriteState = state.favouriteState.copy(infoMessage = null)
        )

        is IsPrimeAPIError -> state.copy(
            favouriteState = state.favouriteState.copy(
                isLoading = false,
                infoMessage = Error(action.error)
            )
        )

        is FavouritePrime -> state.copy(
            favouriteState = FavouriteState(
                state.favouriteState.favourites.add(
                    action.prime
                ), isLoading = false, infoMessage = Success
            )
        )

        is UnFavouritePrime -> state.copy(
            favouriteState = FavouriteState(
                state.favouriteState.favourites.remove(
                    action.prime
                )
            )
        )

        else -> state
    }
}