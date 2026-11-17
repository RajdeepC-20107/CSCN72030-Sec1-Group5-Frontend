package ru.anton2319.absolutcinema.ui.devices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CreateNewFolder
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.anton2319.absolutcinema.model.DeviceListUiState
import ru.anton2319.absolutcinema.ui.components.CinemaTopBar
import ru.anton2319.absolutcinema.ui.components.DeviceRow
import ru.anton2319.absolutcinema.ui.components.MenuAction
import ru.anton2319.absolutcinema.ui.components.OverflowMenu
import ru.anton2319.absolutcinema.ui.components.SectionHeader
import ru.anton2319.absolutcinema.ui.components.inset

@Composable
fun DeviceListScreen(
    state: DeviceListUiState,
    onBack: () -> Unit,
    onDeviceClick: (deviceId: String) -> Unit,
    onAddDevice: () -> Unit,
    onCreateGroup: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CinemaTopBar(
                title = "Device list",
                onBack = onBack,
                actions = {
                    OverflowMenu(
                        listOf(
                            MenuAction("Add device", Icons.Outlined.Add, onClick = onAddDevice),
                            MenuAction("Create group", Icons.Outlined.CreateNewFolder, onClick = onCreateGroup),
                        )
                    )
                },
            )
        },
    ) { padding ->
        LazyColumn(
            contentPadding = padding.inset(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            state.sections.forEach { section ->
                item(key = "header-${section.title}") { SectionHeader(section.title) }
                items(section.devices, key = { it.id }) { device ->
                    DeviceRow(device = device, onClick = { onDeviceClick(device.id) })
                }
            }
        }
    }
}
