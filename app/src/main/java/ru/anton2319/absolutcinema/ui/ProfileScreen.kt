package ru.anton2319.absolutcinema.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.anton2319.absolutcinema.model.InterruptSettings
import ru.anton2319.absolutcinema.ui.theme.AbsolutCinemaTheme

/** Profile: links to Interrupt Mode, with Event Log and Log Out as placeholders for later tickets. */
@Composable
fun ProfileScreen(
    settings: InterruptSettings,
    onInterruptMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(key = "title") {
            Text(text = "Profile", style = MaterialTheme.typography.headlineSmall)
        }
        item(key = "interrupt") {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Interrupt Mode", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = if (settings.noInterrupt) "No Interrupt is on" else "No Interrupt is off",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${settings.importantNumbers.size} important numbers, " +
                            "pause at ${settings.faultThreshold} failed devices",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Button(onClick = onInterruptMode, modifier = Modifier.padding(top = 8.dp)) {
                        Text("Open Interrupt Mode")
                    }
                }
            }
        }
        item(key = "event-log") {
            OutlinedButton(onClick = { }, enabled = false, modifier = Modifier.fillMaxWidth()) {
                Text("Event Log (coming later)")
            }
        }
        item(key = "log-out") {
            OutlinedButton(onClick = { }, enabled = false, modifier = Modifier.fillMaxWidth()) {
                Text("Log Out (coming later)")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    AbsolutCinemaTheme {
        ProfileScreen(settings = InterruptSettings(), onInterruptMode = { })
    }
}
