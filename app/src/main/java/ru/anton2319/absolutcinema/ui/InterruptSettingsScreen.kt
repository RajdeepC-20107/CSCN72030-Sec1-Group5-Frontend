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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.anton2319.absolutcinema.model.ImportantNumber
import ru.anton2319.absolutcinema.model.InterruptSettings
import ru.anton2319.absolutcinema.model.addNumber
import ru.anton2319.absolutcinema.model.removeNumber
import ru.anton2319.absolutcinema.model.withFaultThreshold
import ru.anton2319.absolutcinema.model.withNoInterrupt
import ru.anton2319.absolutcinema.ui.theme.AbsolutCinemaTheme

/**
 * Interrupt Mode: the No Interrupt switch, the fault threshold and the
 * Important Numbers list (US-8, US-12, US-13). Changes are reported through
 * [onSettingsChange]; saving to the backend comes later (SCRUM-18).
 */
@Composable
fun InterruptSettingsScreen(
    settings: InterruptSettings,
    onSettingsChange: (InterruptSettings) -> Unit,
    modifier: Modifier = Modifier
) {
    var number by remember { mutableStateOf("") }
    var label by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(key = "title") {
            Text(text = "Interrupt Mode", style = MaterialTheme.typography.headlineSmall)
        }

        item(key = "no-interrupt") {
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "No Interrupt", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = if (settings.noInterrupt) {
                                "On: calls are ignored. A device fault still pauses the show."
                            } else {
                                "Off: a call from an important number pauses the show."
                            },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Switch(
                        checked = settings.noInterrupt,
                        onCheckedChange = { onSettingsChange(settings.withNoInterrupt(it)) }
                    )
                }
            }
        }

        item(key = "threshold") {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Fault threshold", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "The show pauses when this many devices lose power or disconnect.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextButton(
                            onClick = { onSettingsChange(settings.withFaultThreshold(settings.faultThreshold - 1)) }
                        ) { Text("-") }
                        Text(
                            text = settings.faultThreshold.toString(),
                            style = MaterialTheme.typography.headlineSmall
                        )
                        TextButton(
                            onClick = { onSettingsChange(settings.withFaultThreshold(settings.faultThreshold + 1)) }
                        ) { Text("+") }
                    }
                }
            }
        }

        item(key = "numbers-header") {
            Text(text = "Important Numbers", style = MaterialTheme.typography.titleMedium)
        }

        if (settings.importantNumbers.isEmpty()) {
            item(key = "numbers-empty") {
                Text(text = "No numbers yet. Add one below.", style = MaterialTheme.typography.bodyMedium)
            }
        }

        items(settings.importantNumbers, key = { it.number }) { entry ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = entry.label, style = MaterialTheme.typography.titleMedium)
                        Text(text = entry.number, style = MaterialTheme.typography.bodyMedium)
                    }
                    TextButton(onClick = { onSettingsChange(settings.removeNumber(entry.number)) }) {
                        Text("Remove")
                    }
                }
            }
        }

        item(key = "add-form") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = number,
                    onValueChange = { number = it; error = null },
                    label = { Text("Phone number") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = label,
                    onValueChange = { label = it },
                    label = { Text("Name (optional)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                error?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
                Button(
                    onClick = {
                        val result = settings.addNumber(number, label)
                        if (result.error == null) {
                            onSettingsChange(result.settings)
                            number = ""
                            label = ""
                        } else {
                            error = result.error
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Add number") }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InterruptSettingsScreenPreview() {
    AbsolutCinemaTheme {
        InterruptSettingsScreen(
            settings = InterruptSettings(
                importantNumbers = listOf(ImportantNumber("5195550123", "Mom"))
            ),
            onSettingsChange = { }
        )
    }
}
