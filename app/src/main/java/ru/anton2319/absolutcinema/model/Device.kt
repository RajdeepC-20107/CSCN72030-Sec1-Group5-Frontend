package ru.anton2319.absolutcinema.model

enum class DeviceStatus {
    OK, FAULT, NO_POWER, DISCONNECTED;

    companion object {
        /** Converts the status text used by the backend API (see docs/api-contract.md). */
        fun fromApi(text: String): DeviceStatus = when (text) {
            "OK" -> OK
            "Fault" -> FAULT
            "NoPower" -> NO_POWER
            "Disconnected" -> DISCONNECTED
            else -> FAULT
        }
    }
}

enum class DeviceType {
    LIGHTS, SPEAKERS, POPCORN, AIR_CONDITIONING, PROJECTOR, MOTION;

    companion object {
        /** Converts the type text used by the backend API, or null if it is not known. */
        fun fromApi(text: String): DeviceType? = when (text) {
            "lights" -> LIGHTS
            "speakers" -> SPEAKERS
            "popcorn" -> POPCORN
            "airconditioning" -> AIR_CONDITIONING
            "projector" -> PROJECTOR
            "motion" -> MOTION
            else -> null
        }
    }
}

data class Device(
    val id: String,
    val name: String,
    val type: DeviceType,
    val group: String,
    val value: Double?,
    val unit: String,
    val status: DeviceStatus,
    val timestamp: String
)
