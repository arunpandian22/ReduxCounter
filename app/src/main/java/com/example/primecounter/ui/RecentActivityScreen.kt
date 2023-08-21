package com.example.primecounter.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.primecounter.model.Activity
import com.example.primecounter.redux.AppState
import com.example.primecounter.redux.CounterAppStore
import com.example.primecounter.redux.Dispatch
import com.example.primecounter.redux.Unsubscribe

/**
 * Created by Arun Pandian  on 16/08/23.
 */
var activityDispatch: Dispatch? = null

@Composable
fun RecentActivityScreen() {
    val activities = remember {
        mutableStateOf<List<Activity>>(emptyList())
    }


    DisposableEffect(Unit) {
        val unsubscribe: Unsubscribe =
            CounterAppStore.instance.subscribe { state: AppState, lDispatch: Dispatch ->
                activityDispatch = lDispatch
                activities.value = state.activityState.activities.toMutableList()
            }
        onDispose {
            unsubscribe.invoke()
            activityDispatch = null
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        if (activities.value.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(activities.value) { actions ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)

                    ) {
                        Text(
                            modifier = Modifier
                                .wrapContentHeight()
                                .align(Alignment.Center),
                            text = "${actions.primeNumber} is ${actions.action.action}",
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
                text = "oops! you have not done any actions recently \uD83E\uDD7A",
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }

}