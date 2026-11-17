package ru.anton2319.absolutcinema.model

object SampleData {
    val devices = listOf(
        Device("light-stair-1", "Stairway light 1", DeviceKind.Light, "10 (dim)", isOn = true),
        Device("light-stair-2", "Stairway light 2", DeviceKind.Light, "10 (dim)", isOn = true),
        Device("light-spot-1", "Overhead spot 1", DeviceKind.Light, "0 (off)", isOn = false),
        Device("popcorn-salty", "Salty", DeviceKind.PopcornMachine, "off", isOn = false),
        Device("popcorn-sweet", "Sweet", DeviceKind.PopcornMachine, "off", isOn = false, isPinned = true),
        Device("popcorn-plain", "Plain", DeviceKind.PopcornMachine, "off", isOn = false),
        Device("speaker-left", "Left", DeviceKind.Speaker, "+0.4 dB / +200 ms", isOn = true),
        Device("speaker-right", "Right", DeviceKind.Speaker, "+0.0 dB / +0 ms", isOn = true),
        Device("ac", "Air conditioning", DeviceKind.Climate, "21°C", isOn = true, isPinned = true),
    )

    val deviceList = DeviceListUiState(
        listOf(
            DeviceSection("Lights", devices.filter { it.kind == DeviceKind.Light }),
            DeviceSection("Popcorn machines", devices.filter { it.kind == DeviceKind.PopcornMachine }),
            DeviceSection("Speakers", devices.filter { it.kind == DeviceKind.Speaker }),
            DeviceSection("Climate", devices.filter { it.kind == DeviceKind.Climate }),
        )
    )

    val home = HomeUiState(
        nowPlaying = NowPlaying("Never Gonna Give You Up", "Rick Astley", isPlaying = true),
        room = RoomLayout(
            speakers = listOf(
                SpeakerPlacement(x = 0.17f, y = 0.2f, rotationDegrees = -35f),
                SpeakerPlacement(x = 0.83f, y = 0.2f, rotationDegrees = 35f),
            ),
            seatX = 0.5f,
            seatY = 0.52f,
        ),
        pinned = listOf(
            DeviceControl.Stepper("AC", "21°C"),
            DeviceControl.Toggle("Sweet", isOn = false),
        ),
    )

    val calibration = CalibrationUiState(progress = 0.78f)

    fun deviceDetail(id: String): DeviceDetailUiState {
        val device = devices.firstOrNull { it.id == id } ?: devices.first()
        val controls = when (device.kind) {
            DeviceKind.Light -> listOf(
                DeviceControl.Stepper("Dimmer", "10"),
                DeviceControl.Readout("Temp", "2700K"),
            )
            DeviceKind.PopcornMachine -> listOf(DeviceControl.Readout("Temp", "40°C"))
            DeviceKind.Speaker -> listOf(
                DeviceControl.Stepper("Level", "+0.4 dB"),
                DeviceControl.Stepper("Delay", "200 ms"),
            )
            DeviceKind.Climate -> listOf(
                DeviceControl.Stepper("Target", "21°C"),
                DeviceControl.Readout("Room", "23°C"),
            )
        }
        return DeviceDetailUiState(device, controls)
    }

    val interruptRules = listOf(
        InterruptRule("doorbell", InterruptTrigger.Doorbell, enabled = true),
        InterruptRule("timer", InterruptTrigger.Timer, enabled = false),
        InterruptRule("call", InterruptTrigger.IncomingCall, enabled = true),
    )

    val eventLog = listOf(
        LogEntry("1", "21:42", "Doorbell", "Doorbell interrupted your playback"),
        LogEntry("2", "21:15", "Calibration finished", "Speakers tuned to your position"),
        LogEntry("3", "20:58", "Log in – Netflix", "You logged in to your streaming account on Netflix"),
    )
}
