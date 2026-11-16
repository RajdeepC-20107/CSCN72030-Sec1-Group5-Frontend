package ru.anton2319.absolutcinema.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.anton2319.absolutcinema.data.FakeDevices
import ru.anton2319.absolutcinema.model.Device
import ru.anton2319.absolutcinema.model.DeviceStatus
import ru.anton2319.absolutcinema.model.displayValue
import ru.anton2319.absolutcinema.model.label
import ru.anton2319.absolutcinema.ui.theme.AbsolutCinemaTheme

/**
 * One device with its name, value and status. A device that has a problem is
 * shown in red (or grey when disconnected) and also says so in words.
 */
@Composable
fun DeviceCard(
    device: Device,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val colors = MaterialTheme.colorScheme
    val (container, content) = when (device.status) {
        DeviceStatus.OK -> colors.surfaceVariant to colors.onSurfaceVariant
        DeviceStatus.FAULT, DeviceStatus.NO_POWER -> colors.errorContainer to colors.onErrorContainer
        DeviceStatus.DISCONNECTED -> colors.surfaceContainerHighest to colors.onSurface
    }
    val clickModifier = if (onClick != null) Modifier.clickable { onClick() } else Modifier

    Card(
        modifier = modifier.fillMaxWidth().then(clickModifier),
        colors = CardDefaults.cardColors(containerColor = container, contentColor = content)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = device.name, style = MaterialTheme.typography.titleMedium)
            Text(text = device.displayValue(), style = MaterialTheme.typography.headlineSmall)
            Text(text = device.status.label(), style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeviceCardOkPreview() {
    AbsolutCinemaTheme { DeviceCard(FakeDevices.all.first { it.id == "ac" }) }
}

@Preview(showBackground = true)
@Composable
fun DeviceCardNoPowerPreview() {
    AbsolutCinemaTheme { DeviceCard(FakeDevices.withFaults.first { it.id == "projector" }) }
}

@Preview(showBackground = true)
@Composable
fun DeviceCardDisconnectedPreview() {
    AbsolutCinemaTheme { DeviceCard(FakeDevices.withFaults.first { it.id == "right" }) }
}
