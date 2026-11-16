package ru.anton2319.absolutcinema.model

import org.junit.Assert.assertEquals
import org.junit.Test

class DeviceDisplayTest {
    private fun device(type: DeviceType, value: Double?, unit: String, status: DeviceStatus = DeviceStatus.OK) =
        Device("id", "Name", type, "Group", value, unit, status, "2026-11-16T19:42:10Z")

    @Test
    fun statusLabelsUseWords() {
        assertEquals("OK", DeviceStatus.OK.label())
        assertEquals("Fault", DeviceStatus.FAULT.label())
        assertEquals("No power", DeviceStatus.NO_POWER.label())
        assertEquals("Disconnected", DeviceStatus.DISCONNECTED.label())
    }

    @Test
    fun lightsShowDimLevelOrOff() {
        assertEquals("10 (dim)", device(DeviceType.LIGHTS, 10.0, "level").displayValue())
        assertEquals("0 (off)", device(DeviceType.LIGHTS, 0.0, "level").displayValue())
    }

    @Test
    fun popcornShowsOnOrOff() {
        assertEquals("On", device(DeviceType.POPCORN, 1.0, "").displayValue())
        assertEquals("Off", device(DeviceType.POPCORN, 0.0, "").displayValue())
    }

    @Test
    fun airConditioningShowsDegrees() {
        assertEquals("21 °C", device(DeviceType.AIR_CONDITIONING, 21.0, "°C").displayValue())
        assertEquals("21.5 °C", device(DeviceType.AIR_CONDITIONING, 21.5, "°C").displayValue())
    }

    @Test
    fun otherDevicesShowValueAndUnit() {
        assertEquals("80 %", device(DeviceType.PROJECTOR, 80.0, "%").displayValue())
    }

    @Test
    fun deviceWithoutPowerOrConnectionShowsDashes() {
        assertEquals("--", device(DeviceType.PROJECTOR, 80.0, "%", DeviceStatus.NO_POWER).displayValue())
        assertEquals("--", device(DeviceType.SPEAKERS, 60.0, "%", DeviceStatus.DISCONNECTED).displayValue())
        assertEquals("--", device(DeviceType.SPEAKERS, null, "%").displayValue())
    }
}
