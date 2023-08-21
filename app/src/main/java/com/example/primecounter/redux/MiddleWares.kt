package com.example.primecounter.redux

import android.util.Log
import com.example.primecounter.data.APIService
import com.example.primecounter.model.Activity
import com.example.primecounter.model.Favourite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * Created by Arun Pandian  on 21/08/23.
 */
fun loggerMiddleware(
    state: AppState,
    action: Action,
    dispatch: Dispatch,
    next: Next<AppState>,
): Action {
    Log.d("middleware", "action in <-- $action")
    val newAction = next(state, action, dispatch)
    Log.d("middleware", "action out --> $newAction")
    return newAction
}


fun isPrimeMiddleWare(
    state: AppState,
    action: Action,
    dispatch: Dispatch,
    next: Next<AppState>,
): Action {
    return when (action) {
        is VerifyFavorites -> {
            if (!state.favouriteState.isAlreadyFavorite(action.prime)) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val result = APIService.instance.getAPIService().isPrimeNumber(action.prime)
                        if (result.isPrime) {
                            dispatch(FavouritePrime(action.prime))
                            dispatch(AddActivity(Activity(action.prime, Favourite)))
                        } else
                            dispatch(IsPrimeAPIError("${action.prime} is not a prime number"))

                    } catch (e: Exception) {
                        dispatch(IsPrimeAPIError("${e.localizedMessage} "))
                    }

                }
                next(state, LoadingIsPrimeAPI, dispatch)
            } else {
                next(state, IsPrimeAPIError("already exist"), dispatch)
            }
        }

        else -> next(state, action, dispatch)
    }
}