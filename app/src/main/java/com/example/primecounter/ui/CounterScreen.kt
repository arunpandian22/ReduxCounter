package com.example.primecounter.ui

import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.primecounter.model.AlreadyExist
import com.example.primecounter.model.Error
import com.example.primecounter.model.Success
import com.example.primecounter.redux.AppState
import com.example.primecounter.redux.ClearPrimeMessage
import com.example.primecounter.redux.CounterAppStore
import com.example.primecounter.redux.Decrement
import com.example.primecounter.redux.Dispatch
import com.example.primecounter.redux.Increment
import com.example.primecounter.redux.Unsubscribe
import com.example.primecounter.redux.VerifyFavorites


/**
 * Created by Arun Pandian  on 16/08/23.
 */
object CounterScreenCons {
    val tag = "CounterScreen"
}



@Composable
fun CounterScreen() {
    var dispatch: Dispatch? = null
    val context = LocalContext.current
    val isLoading = remember {
        mutableStateOf(false)
    }
    var number by remember { mutableLongStateOf(0) }


    DisposableEffect(Unit) {
        val unsubscribe: Unsubscribe =
            CounterAppStore.instance.subscribe { state: AppState, lDispatch: Dispatch ->
                dispatch = lDispatch
                number = state.counterState.count
                isLoading.value = state.favouriteState.isLoading
                val infoMessage = state.favouriteState.infoMessage

                if (infoMessage != null) {

                    val handler = android.os.Handler(Looper.getMainLooper())

                    handler.post {
                        when (infoMessage) {
                            is AlreadyExist -> {
                                Toast.makeText(context, "already exist", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            is Error -> {
                                Toast.makeText(context, infoMessage.message, Toast.LENGTH_SHORT)
                                    .show()
                            }

                            is Success -> {
                                Toast.makeText(context, "Favourite added", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }
                        dispatch?.invoke(ClearPrimeMessage)
                    }


                }
            }
        onDispose {
            unsubscribe.invoke()
            dispatch = null
            Log.d("DisposableEffect", "disposed: $unsubscribe")
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        if (isLoading.value) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color.White)
            ) {
                CircularProgressIndicator()
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 10.dp, alignment = Alignment.CenterVertically
            ), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Current number: ${number}")
            Button(onClick = { dispatch?.invoke(Increment) }) {
                Text("Increment")
            }
            Button(onClick = {
                if (number > 0)
                    dispatch?.invoke(Decrement)
                else {
                    Toast.makeText(
                        context,
                        "can't favourite any negative value",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }) {
                Text("Decrement")
            }
            Button(onClick = {
                dispatch?.invoke(VerifyFavorites(number))
            }) {
                Text("Favourite")
            }
        }

    }


}