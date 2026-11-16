package ru.anton2319.absolutcinema.data

import ru.anton2319.absolutcinema.model.Device
import ru.anton2319.absolutcinema.model.DeviceStatus
import ru.anton2319.absolutcinema.model.DeviceType

/**
 * Fake devices that follow the team wireframe, so the screens can be built
 * before the backend exists. Replace with the backend data (SCRUM-18).
 */
object FakeDevices {
    private const val NOW = "2026-11-16T19:42:10Z"

    private fun device(
        id: String,
        name: String,
        type: DeviceType,
        group: String,
        value: Double?,
        unit: String,
        status: DeviceStatus = DeviceStatus.OK
    ) = Device(id, name, type, group, value, unit, status, NOW)

    val all: List<Device> = listOf(
        device("stairway1", "Stairway Light 1", DeviceType.LIGHTS, "Lights", 10.0, "level"),
        device("stairway2", "Stairway Light 2", DeviceType.LIGHTS, "Lights", 10.0, "level"),
        device("overhead1", "Overhead Spot 1", DeviceType.LIGHTS, "Lights", 0.0, "level"),
        device("salty", "Salty", DeviceType.POPCORN, "Popcorn Machines", 0.0, ""),
        device("sweet", "Sweet", DeviceType.POPCORN, "Popcorn Machines", 0.0, ""),
        device("plain", "Plain", DeviceType.POPCORN, "Popcorn Machines", 0.0, ""),
        device("left", "Left", DeviceType.SPEAKERS, "Speakers", 60.0, "%"),
        device("right", "Right", DeviceType.SPEAKERS, "Speakers", 60.0, "%"),
        device("ac", "Air Conditioning", DeviceType.AIR_CONDITIONING, "Air Conditioning", 21.0, "°C"),
        device("projector", "Projector", DeviceType.PROJECTOR, "Projector", 80.0, "%")
    )

    /** Ids of the devices shown in the Pinned section of the Home screen. */
    val pinnedIds: List<String> = listOf("ac", "sweet")

    /** The same devices with two problems, for checking the fault look on screen. */
    val withFaults: List<Device> = all.map {
        when (it.id) {
            "projector" -> it.copy(status = DeviceStatus.NO_POWER)
            "right" -> it.copy(status = DeviceStatus.DISCONNECTED)
            else -> it
        }
    }
}
