package ru.anton2319.absolutcinema.model

enum class DeviceKind { Light, PopcornMachine, Speaker, Climate }

data class Device(
    val id: String,
    val name: String,
    val kind: DeviceKind,
    val summary: String,
    val isOn: Boolean,
    val isPinned: Boolean = false,
)

data class DeviceSection(val title: String, val devices: List<Device>)

sealed interface DeviceControl {
    val label: String

    data class Stepper(override val label: String, val value: String) : DeviceControl

    data class Toggle(override val label: String, val isOn: Boolean) : DeviceControl

    data class Readout(override val label: String, val value: String) : DeviceControl
}

data class NowPlaying(val title: String, val artist: String, val isPlaying: Boolean)

data class SpeakerPlacement(val x: Float, val y: Float, val rotationDegrees: Float)

data class RoomLayout(
    val speakers: List<SpeakerPlacement>,
    val seatX: Float,
    val seatY: Float,
)

data class HomeUiState(
    val nowPlaying: NowPlaying,
    val room: RoomLayout,
    val pinned: List<DeviceControl>,
)

data class CalibrationUiState(val progress: Float)

data class DeviceListUiState(val sections: List<DeviceSection>)

data class DeviceDetailUiState(val device: Device, val controls: List<DeviceControl>)

enum class InterruptTrigger(val label: String) {
    Doorbell("Doorbell"),
    Timer("Timer"),
    IncomingCall("Incoming call"),
}

data class InterruptRule(
    val id: String,
    val trigger: InterruptTrigger,
    val enabled: Boolean,
)

data class LogEntry(val id: String, val time: String, val title: String, val detail: String)
