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
import ru.anton2319.absolutcinema.model.InterruptSettings

private enum class Screen { HOME, DEVICE_LIST, PROFILE, INTERRUPT }

/**
 * The app. Home opens the Device List (See All) and Profile; Profile opens
 * Interrupt Mode. Back goes up one level.
 */
@Composable
fun AbsolutCinemaApp() {
    var screen by remember { mutableStateOf(Screen.HOME) }
    var settings by remember { mutableStateOf(InterruptSettings()) }
    val devices = FakeDevices.all

    BackHandler(enabled = screen != Screen.HOME) {
        screen = if (screen == Screen.INTERRUPT) Screen.PROFILE else Screen.HOME
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val padded = Modifier.padding(innerPadding)
        when (screen) {
            Screen.HOME -> HomeScreen(
                devices = devices,
                pinnedIds = FakeDevices.pinnedIds,
                onSeeAll = { screen = Screen.DEVICE_LIST },
                modifier = padded,
                noInterrupt = settings.noInterrupt,
                onProfile = { screen = Screen.PROFILE }
            )
            Screen.DEVICE_LIST -> DeviceListScreen(devices = devices, modifier = padded)
            Screen.PROFILE -> ProfileScreen(
                settings = settings,
                onInterruptMode = { screen = Screen.INTERRUPT },
                modifier = padded
            )
            Screen.INTERRUPT -> InterruptSettingsScreen(
                settings = settings,
                onSettingsChange = { settings = it },
                modifier = padded
            )
        }
    }
}
