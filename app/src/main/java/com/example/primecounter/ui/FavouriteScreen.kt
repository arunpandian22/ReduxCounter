package com.example.primecounter.ui

import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.primecounter.model.Activity
import com.example.primecounter.model.UnFavourite
import com.example.primecounter.redux.AddActivity
import com.example.primecounter.redux.AppState
import com.example.primecounter.redux.CounterAppStore
import com.example.primecounter.redux.Dispatch
import com.example.primecounter.redux.UnFavouritePrime
import com.example.primecounter.redux.Unsubscribe


/**
 * Created by Arun Pandian  on 16/08/23.
 */
var dispatch1: Dispatch? = null
var selectedNumber = -1L

@Composable
fun FavouriteScreen() {

//    val viewModel: FavouriteViewModel = hiltViewModel()
    val favourites = remember {
        mutableStateOf<List<Long>>(emptyList())
    }

    val openDialog = remember { mutableStateOf(false) }


    DisposableEffect(Unit) {
        val unsubscribe: Unsubscribe =
            CounterAppStore.instance.subscribe { state: AppState, lDispatch: Dispatch ->
                dispatch1 = lDispatch
                favourites.value = state.favouriteState.favourites.toMutableList()
            }
        onDispose {
            unsubscribe.invoke()
            dispatch1 = null
        }
    }



    Box(modifier = Modifier.fillMaxSize()) {
        if (favourites.value.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(favourites.value) { index, favouriteNumber ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(onLongPress = {
                                selectedNumber = favourites.value[index]
                                Log.d(
                                    "FavouriteScreen",
                                    "num $selectedNumber $dispatch1  ${favourites.value[index]}"
                                )
                                openDialog.value = true
                            })
                        }) {
                        Text(
                            modifier = Modifier
                                .wrapContentHeight()
                                .align(Alignment.Center),
                            text = favouriteNumber.toString(),
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                        Divider(color = Color.Black)
                    }

                }
            }
        } else {
            Text(
                modifier = Modifier
                    .wrapContentHeight()
                    .align(Alignment.Center),
                text = "Oops! No favourites here \uD83E\uDD7A",
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
    dispatch1?.let { DeleteAlertDialog(openDialog = openDialog, dispatch = it) }
}


@Composable
fun DeleteAlertDialog(
    openDialog: MutableState<Boolean>,
    dispatch: Dispatch,
) {
    if (openDialog.value) {
        AlertDialog(onDismissRequest = {
            openDialog.value = false
        }, properties = DialogProperties(
            dismissOnClickOutside = false
        ), title = {
            Text(text = "Unfavourite!!!", textAlign = TextAlign.Center)
        }, text = {
            Text(
                "unfavourite the prime number ${selectedNumber}", textAlign = TextAlign.Center
            )
        }, confirmButton = {
            Button(

                onClick = {
                    dispatch.invoke(UnFavouritePrime(selectedNumber))
                    dispatch(AddActivity(Activity(selectedNumber, UnFavourite)))
                    openDialog.value = false
                }) {
                Text("Unfavourite", textAlign = TextAlign.Center)
            }
        }, dismissButton = {
            Button(onClick = {
                openDialog.value = false
            }) {
                Text("Cancel", textAlign = TextAlign.Center)
            }
        })
    }


}