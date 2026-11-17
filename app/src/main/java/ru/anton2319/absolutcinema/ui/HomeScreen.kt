package ru.anton2319.absolutcinema.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.anton2319.absolutcinema.data.FakeDevices
import ru.anton2319.absolutcinema.model.Device
import ru.anton2319.absolutcinema.ui.theme.AbsolutCinemaTheme

/** The first screen: now playing, the seating position, pinned devices and See All. */
@Composable
fun HomeScreen(
    devices: List<Device>,
    pinnedIds: List<String>,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier,
    noInterrupt: Boolean = false,
    onProfile: () -> Unit = {}
) {
    val pinned = devices.filter { it.id in pinnedIds }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(key = "title") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "AbsolutCinema", style = MaterialTheme.typography.headlineMedium)
                TextButton(onClick = onProfile) { Text("Profile") }
            }
        }
        if (noInterrupt) {
            item(key = "no-interrupt") {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "No Interrupt is on",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
        item(key = "now-playing") { NowPlayingCard() }
        item(key = "seating") { SeatingCard(seat = "B3") }
        item(key = "pinned-header") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Pinned", style = MaterialTheme.typography.titleMedium)
                TextButton(onClick = { /* editing pins comes in a later ticket */ }) {
                    Text("Edit")
                }
            }
        }
        items(pinned, key = { it.id }) { device ->
            DeviceCard(device = device)
        }
        item(key = "see-all") {
            Button(onClick = onSeeAll, modifier = Modifier.fillMaxWidth()) {
                Text("See All")
            }
        }
    }
}

/** Fake playback controls. Start, Pause and Resume will come from the Hub (FR-007). */
@Composable
private fun NowPlayingCard() {
    var playing by remember { mutableStateOf(true) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Now playing", style = MaterialTheme.typography.labelLarge)
            Text(text = "No title selected", style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextButton(onClick = { }) { Text("Previous") }
                TextButton(onClick = { playing = !playing }) {
                    Text(if (playing) "Pause" else "Resume")
                }
                TextButton(onClick = { }) { Text("Next") }
            }
        }
    }
}

@Composable
private fun SeatingCard(seat: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Speakers", style = MaterialTheme.typography.titleMedium)
            Text(text = "Seating position: $seat", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    AbsolutCinemaTheme {
        HomeScreen(
            devices = FakeDevices.all,
            pinnedIds = FakeDevices.pinnedIds,
            onSeeAll = { }
        )
    }
}
