package ru.anton2319.absolutcinema.model

private fun formatNumber(number: Double): String =
    if (number % 1.0 == 0.0) number.toLong().toString() else number.toString()

/** The words shown for a status. Colour is never the only signal (UR-004). */
fun DeviceStatus.label(): String = when (this) {
    DeviceStatus.OK -> "OK"
    DeviceStatus.FAULT -> "Fault"
    DeviceStatus.NO_POWER -> "No power"
    DeviceStatus.DISCONNECTED -> "Disconnected"
}

/** The value text shown on a device card or row. */
fun Device.displayValue(): String {
    if (status == DeviceStatus.NO_POWER || status == DeviceStatus.DISCONNECTED) return "--"
    val v = value ?: return "--"
    return when (type) {
        DeviceType.POPCORN -> if (v > 0) "On" else "Off"
        DeviceType.LIGHTS -> if (v > 0) "${formatNumber(v)} (dim)" else "0 (off)"
        DeviceType.AIR_CONDITIONING -> "${formatNumber(v)} °C"
        else -> "${formatNumber(v)} $unit".trim()
    }
}
