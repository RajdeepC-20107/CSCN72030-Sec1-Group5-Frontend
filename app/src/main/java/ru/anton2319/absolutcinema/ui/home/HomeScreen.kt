package ru.anton2319.absolutcinema.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.anton2319.absolutcinema.model.HomeUiState
import ru.anton2319.absolutcinema.model.NowPlaying
import ru.anton2319.absolutcinema.ui.components.ControlRow
import ru.anton2319.absolutcinema.ui.components.OutlinedPanel
import ru.anton2319.absolutcinema.ui.components.inset

@Composable
fun HomeScreen(
    state: HomeUiState,
    onProfileClick: () -> Unit,
    onCalibrateClick: () -> Unit,
    onSeeAllDevicesClick: () -> Unit,
    modifier: Modifier = Modifier,
    onEditPinnedClick: () -> Unit = {},
    onPreviousTrack: () -> Unit = {},
    onPlayPause: () -> Unit = {},
    onNextTrack: () -> Unit = {},
    onPinnedStep: (index: Int, delta: Int) -> Unit = { _, _ -> },
    onPinnedToggle: (index: Int, on: Boolean) -> Unit = { _, _ -> },
) {
    Scaffold(modifier = modifier) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding.inset()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "AbsolutCinema",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f),
                )
                IconButton(onClick = onProfileClick) {
                    Icon(Icons.Outlined.AccountCircle, contentDescription = "Profile", modifier = Modifier.size(28.dp))
                }
            }

            NowPlayingCard(state.nowPlaying, onPreviousTrack, onPlayPause, onNextTrack)

            TextButton(onClick = onCalibrateClick, modifier = Modifier.align(Alignment.End)) {
                Text("Calibrate")
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 4.dp).size(18.dp),
                )
            }

            RoomMap(state.room)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.PushPin,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    "Pinned",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 6.dp).weight(1f),
                )
                TextButton(onClick = onEditPinnedClick) { Text("Edit") }
            }

            state.pinned.forEachIndexed { index, control ->
                ControlRow(
                    control = control,
                    onStep = { delta -> onPinnedStep(index, delta) },
                    onToggle = { on -> onPinnedToggle(index, on) },
                )
            }

            OutlinedButton(onClick = onSeeAllDevicesClick, modifier = Modifier.fillMaxWidth()) {
                Text("See all")
            }
        }
    }
}

@Composable
private fun NowPlayingCard(
    nowPlaying: NowPlaying,
    onPrevious: () -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
) {
    OutlinedPanel(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.outlineVariant),
                contentAlignment = Alignment.Center,
            ) {
                Icon(Icons.Outlined.MusicNote, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column(Modifier.weight(1f)) {
                Text(
                    nowPlaying.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    nowPlaying.artist,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            IconButton(onClick = onPrevious, modifier = Modifier.size(40.dp)) {
                Icon(Icons.Filled.SkipPrevious, contentDescription = "Previous track")
            }
            IconButton(onClick = onPlayPause, modifier = Modifier.size(40.dp)) {
                Icon(
                    if (nowPlaying.isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    contentDescription = if (nowPlaying.isPlaying) "Pause" else "Play",
                )
            }
            IconButton(onClick = onNext, modifier = Modifier.size(40.dp)) {
                Icon(Icons.Filled.SkipNext, contentDescription = "Next track")
            }
        }
    }
}
