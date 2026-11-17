package ru.anton2319.absolutcinema.ui.devices

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.DriveFileRenameOutline
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.FlashlightOn
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.Speaker
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.anton2319.absolutcinema.model.DeviceDetailUiState
import ru.anton2319.absolutcinema.model.DeviceKind
import ru.anton2319.absolutcinema.ui.components.CinemaTopBar
import ru.anton2319.absolutcinema.ui.components.ControlRow
import ru.anton2319.absolutcinema.ui.components.MenuAction
import ru.anton2319.absolutcinema.ui.components.OverflowMenu
import ru.anton2319.absolutcinema.ui.components.inset

@Composable
fun DeviceReadingsScreen(
    state: DeviceDetailUiState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onPowerChange: (on: Boolean) -> Unit = {},
    onStep: (controlIndex: Int, delta: Int) -> Unit = { _, _ -> },
    onRename: (newName: String) -> Unit = {},
    onRemove: () -> Unit = {},
    onTogglePin: () -> Unit = {},
) {
    val device = state.device
    var showRename by remember { mutableStateOf(false) }
    var showRemove by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            CinemaTopBar(
                title = device.name,
                onBack = onBack,
                actions = {
                    OverflowMenu(
                        listOf(
                            MenuAction("Rename", Icons.Outlined.DriveFileRenameOutline, onClick = { showRename = true }),
                            MenuAction(
                                "Remove",
                                Icons.Outlined.Delete,
                                tint = MaterialTheme.colorScheme.tertiary,
                                onClick = { showRemove = true },
                            ),
                            MenuAction(
                                if (device.isPinned) "Unpin" else "Pin",
                                Icons.Outlined.PushPin,
                                onClick = onTogglePin,
                            ),
                        )
                    )
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding.inset()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .size(140.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outline, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = device.kind.icon(),
                    contentDescription = null,
                    modifier = Modifier.size(72.dp),
                    tint = if (device.isOn) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PowerButton("Off", MaterialTheme.colorScheme.tertiary, selected = !device.isOn, onClick = { onPowerChange(false) }, modifier = Modifier.weight(1f))
                PowerButton("On", MaterialTheme.colorScheme.secondary, selected = device.isOn, onClick = { onPowerChange(true) }, modifier = Modifier.weight(1f))
            }

            state.controls.forEachIndexed { index, control ->
                ControlRow(control = control, onStep = { delta -> onStep(index, delta) })
            }
        }
    }

    if (showRename) {
        RenameDialog(
            current = device.name,
            onDismiss = { showRename = false },
            onConfirm = { name ->
                showRename = false
                onRename(name)
            },
        )
    }
    if (showRemove) {
        AlertDialog(
            onDismissRequest = { showRemove = false },
            title = { Text("Remove ${device.name}?") },
            text = { Text("It will no longer appear in the device list or in Pinned.") },
            confirmButton = {
                TextButton(onClick = { showRemove = false; onRemove() }) {
                    Text("Remove", color = MaterialTheme.colorScheme.tertiary)
                }
            },
            dismissButton = { TextButton(onClick = { showRemove = false }) { Text("Cancel") } },
        )
    }
}

@Composable
private fun PowerButton(
    label: String,
    color: Color,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) color.copy(alpha = 0.16f) else Color.Transparent,
            contentColor = color,
        ),
        border = BorderStroke(1.dp, color),
    ) { Text(label) }
}

@Composable
private fun RenameDialog(current: String, onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var text by remember { mutableStateOf(current) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Rename device") },
        text = { OutlinedTextField(value = text, onValueChange = { text = it }, singleLine = true) },
        confirmButton = { TextButton(onClick = { onConfirm(text.trim()) }, enabled = text.isNotBlank()) { Text("Save") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } },
    )
}

private fun DeviceKind.icon() = when (this) {
    DeviceKind.Light -> Icons.Outlined.FlashlightOn
    DeviceKind.PopcornMachine -> Icons.Outlined.Fastfood
    DeviceKind.Speaker -> Icons.Outlined.Speaker
    DeviceKind.Climate -> Icons.Outlined.AcUnit
}
