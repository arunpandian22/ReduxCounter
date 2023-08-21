package com.example.primecounter.utils

import com.example.primecounter.utils.Constants.ACTIVITY_SCREEN
import com.example.primecounter.utils.Constants.COUNTER_SCREEN
import com.example.primecounter.utils.Constants.FAVOURITE_SCREEN
import com.example.primecounter.utils.Constants.LIST_SCREEN


/**
 * Created by Arun Pandian  on 16/08/23.
 */
object Constants {
    const val LIST_SCREEN = "ListScreen"
    const val COUNTER_SCREEN = "CounterScreen"
    const val FAVOURITE_SCREEN = "FavouriteScreen"
    const val ACTIVITY_SCREEN = "ActivityScreen"
}

sealed class NavScreens(val route: String)
object ListScreen : NavScreens(LIST_SCREEN)
object CounterScreen : NavScreens(COUNTER_SCREEN)
object FavouriteScreen : NavScreens(FAVOURITE_SCREEN)
object ActivityScreen : NavScreens(ACTIVITY_SCREEN)
