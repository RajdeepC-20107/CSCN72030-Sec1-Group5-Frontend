package ru.anton2319.absolutcinema.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.anton2319.absolutcinema.data.FakeDevices
import ru.anton2319.absolutcinema.model.Device
import ru.anton2319.absolutcinema.ui.theme.AbsolutCinemaTheme

/** Every device, grouped under a heading such as Lights or Popcorn Machines. */
@Composable
fun DeviceListScreen(
    devices: List<Device>,
    modifier: Modifier = Modifier,
    onDeviceClick: (Device) -> Unit = {}
) {
    val groups = devices.groupBy { it.group }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(key = "title") {
            Text(text = "Device List", style = MaterialTheme.typography.headlineSmall)
        }
        groups.forEach { (groupName, rows) ->
            item(key = "header-$groupName") {
                Text(
                    text = groupName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            items(rows, key = { it.id }) { device ->
                DeviceCard(device = device, onClick = { onDeviceClick(device) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeviceListScreenPreview() {
    AbsolutCinemaTheme { DeviceListScreen(devices = FakeDevices.withFaults) }
}
