package ru.anton2319.absolutcinema.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.anton2319.absolutcinema.model.SampleData
import ru.anton2319.absolutcinema.ui.calibrate.CalibrateScreen
import ru.anton2319.absolutcinema.ui.devices.DeviceListScreen
import ru.anton2319.absolutcinema.ui.devices.DeviceReadingsScreen
import ru.anton2319.absolutcinema.ui.home.HomeScreen
import ru.anton2319.absolutcinema.ui.interrupt.EventLogScreen
import ru.anton2319.absolutcinema.ui.interrupt.InterruptModeScreen
import ru.anton2319.absolutcinema.ui.profile.ProfileScreen
import ru.anton2319.absolutcinema.ui.theme.AbsolutCinemaTheme

private const val PHONE = "spec:width=392dp,height=852dp,dpi=420"

@Preview(device = PHONE, showSystemUi = true)
@Composable
private fun HomePreview() = AbsolutCinemaTheme {
    HomeScreen(SampleData.home, onProfileClick = {}, onCalibrateClick = {}, onSeeAllDevicesClick = {})
}

@Preview(device = PHONE, showSystemUi = true)
@Composable
private fun CalibratePreview() = AbsolutCinemaTheme {
    CalibrateScreen(SampleData.calibration, onCancel = {})
}

@Preview(device = PHONE, showSystemUi = true)
@Composable
private fun ProfilePreview() = AbsolutCinemaTheme {
    ProfileScreen(onBack = {}, onAccountClick = {}, onInterruptModeClick = {}, onEventLogClick = {}, onLogOut = {})
}

@Preview(device = PHONE, showSystemUi = true)
@Composable
private fun DeviceListPreview() = AbsolutCinemaTheme {
    DeviceListScreen(SampleData.deviceList, onBack = {}, onDeviceClick = {}, onAddDevice = {}, onCreateGroup = {})
}

@Preview(device = PHONE, showSystemUi = true)
@Composable
private fun DeviceReadingsPreview() = AbsolutCinemaTheme {
    DeviceReadingsScreen(SampleData.deviceDetail("light-spot-1"), onBack = {})
}

@Preview(device = PHONE, showSystemUi = true)
@Composable
private fun InterruptModePreview() = AbsolutCinemaTheme {
    InterruptModeScreen(SampleData.interruptRules, onBack = {})
}

@Preview(device = PHONE, showSystemUi = true)
@Composable
private fun EventLogPreview() = AbsolutCinemaTheme {
    EventLogScreen(SampleData.eventLog, onBack = {})
}
