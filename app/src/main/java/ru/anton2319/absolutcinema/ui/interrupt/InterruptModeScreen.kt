package ru.anton2319.absolutcinema.ui.interrupt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.anton2319.absolutcinema.model.InterruptRule
import ru.anton2319.absolutcinema.model.InterruptTrigger
import ru.anton2319.absolutcinema.ui.components.CinemaTopBar
import ru.anton2319.absolutcinema.ui.components.OutlinedPanel
import ru.anton2319.absolutcinema.ui.components.inset

@Composable
fun InterruptModeScreen(
    rules: List<InterruptRule>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onRuleToggle: (id: String, enabled: Boolean) -> Unit = { _, _ -> },
    onRuleClick: (id: String) -> Unit = {},
) {
    Scaffold(modifier = modifier, topBar = { CinemaTopBar("Interrupt mode", onBack) }) { padding ->
        LazyColumn(contentPadding = padding.inset(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            item {
                Text(
                    "Choose what events interrupt the playback.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
            }
            items(rules, key = { it.id }) { rule ->
                OutlinedPanel(Modifier.fillMaxWidth(), onClick = { onRuleClick(rule.id) }) {
                    Row(
                        Modifier.padding(start = 16.dp, end = 12.dp, top = 12.dp, bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Icon(rule.trigger.icon(), contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Column(Modifier.weight(1f)) {
                            Text(rule.trigger.label, style = MaterialTheme.typography.titleMedium)
                        }
                        Switch(checked = rule.enabled, onCheckedChange = { onRuleToggle(rule.id, it) })
                    }
                }
            }
        }
    }
}

private fun InterruptTrigger.icon() = when (this) {
    InterruptTrigger.Doorbell -> Icons.Outlined.Notifications
    InterruptTrigger.Timer -> Icons.Outlined.Timer
    InterruptTrigger.IncomingCall -> Icons.Outlined.Call
}
