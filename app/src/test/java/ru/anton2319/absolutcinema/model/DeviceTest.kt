package ru.anton2319.absolutcinema.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DeviceTest {
    @Test
    fun statusFromApiMapsKnownValues() {
        assertEquals(DeviceStatus.OK, DeviceStatus.fromApi("OK"))
        assertEquals(DeviceStatus.FAULT, DeviceStatus.fromApi("Fault"))
        assertEquals(DeviceStatus.NO_POWER, DeviceStatus.fromApi("NoPower"))
        assertEquals(DeviceStatus.DISCONNECTED, DeviceStatus.fromApi("Disconnected"))
    }

    @Test
    fun unknownStatusIsTreatedAsFault() {
        assertEquals(DeviceStatus.FAULT, DeviceStatus.fromApi("???"))
    }

    @Test
    fun typeFromApiMapsKnownValuesAndRejectsUnknown() {
        assertEquals(DeviceType.LIGHTS, DeviceType.fromApi("lights"))
        assertEquals(DeviceType.AIR_CONDITIONING, DeviceType.fromApi("airconditioning"))
        assertNull(DeviceType.fromApi("toaster"))
    }
}
