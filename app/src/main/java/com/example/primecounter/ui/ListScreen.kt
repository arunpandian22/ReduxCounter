package com.example.primecounter.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.primecounter.utils.ActivityScreen
import com.example.primecounter.utils.CounterScreen
import com.example.primecounter.utils.FavouriteScreen
import com.example.primecounter.utils.NavScreens


/**
 * Created by Arun Pandian  on 16/08/23.
 */
@Composable
fun ListScreen(navController: NavController) {
    val messages: List<NavScreens> = listOf(CounterScreen, FavouriteScreen, ActivityScreen)

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(messages) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .selectable(selected = true, onClick = {
                        navController.navigate(it.route)
                    })
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .align(Alignment.Center),
                    text = it.route,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                Divider(color = Color.Black)
            }

        }
    }
}