package ru.anton2319.absolutcinema.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.anton2319.absolutcinema.data.FakeDevices

private enum class Screen { HOME, DEVICE_LIST }

/** The app: Home, with See All opening the Device List. Back returns to Home. */
@Composable
fun AbsolutCinemaApp() {
    var screen by remember { mutableStateOf(Screen.HOME) }
    val devices = FakeDevices.all

    BackHandler(enabled = screen != Screen.HOME) { screen = Screen.HOME }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        when (screen) {
            Screen.HOME -> HomeScreen(
                devices = devices,
                pinnedIds = FakeDevices.pinnedIds,
                onSeeAll = { screen = Screen.DEVICE_LIST },
                modifier = Modifier.padding(innerPadding)
            )
            Screen.DEVICE_LIST -> DeviceListScreen(
                devices = devices,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
