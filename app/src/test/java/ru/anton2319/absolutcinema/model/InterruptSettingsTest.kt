package ru.anton2319.absolutcinema.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class InterruptSettingsTest {
    @Test
    fun defaultsMatchTheSrs() {
        val s = InterruptSettings()
        assertFalse(s.noInterrupt)
        assertTrue(s.importantNumbers.isEmpty())
        assertEquals(2, s.faultThreshold)
    }

    @Test
    fun normalizeKeepsOnlyDigits() {
        assertEquals("5195550123", normalizeNumber("(519) 555-0123"))
    }

    @Test
    fun addNumberStoresDigitsAndLabel() {
        val result = InterruptSettings().addNumber("(519) 555-0123", " Mom ")
        assertNull(result.error)
        assertEquals(listOf(ImportantNumber("5195550123", "Mom")), result.settings.importantNumbers)
    }

    @Test
    fun blankLabelFallsBackToTheNumber() {
        val result = InterruptSettings().addNumber("5195550123", "  ")
        assertEquals("5195550123", result.settings.importantNumbers.single().label)
    }

    @Test
    fun tooShortOrTooLongNumbersAreRejected() {
        val short = InterruptSettings().addNumber("12345", "x")
        assertEquals("Enter a phone number with 7 to 15 digits", short.error)
        assertTrue(short.settings.importantNumbers.isEmpty())

        val long = InterruptSettings().addNumber("1234567890123456", "x")
        assertEquals("Enter a phone number with 7 to 15 digits", long.error)
    }

    @Test
    fun duplicateNumbersAreRejected() {
        val once = InterruptSettings().addNumber("5195550123", "Mom").settings
        val twice = once.addNumber("519-555-0123", "Mom again")
        assertEquals("That number is already in the list", twice.error)
        assertEquals(1, twice.settings.importantNumbers.size)
    }

    @Test
    fun removeNumberDeletesOnlyThatNumber() {
        val s = InterruptSettings()
            .addNumber("5195550123", "Mom").settings
            .addNumber("5195550999", "Boss").settings
            .removeNumber("5195550123")
        assertEquals(listOf("5195550999"), s.importantNumbers.map { it.number })
    }

    @Test
    fun faultThresholdStaysBetweenOneAndTen() {
        assertEquals(1, InterruptSettings().withFaultThreshold(0).faultThreshold)
        assertEquals(10, InterruptSettings().withFaultThreshold(99).faultThreshold)
        assertEquals(3, InterruptSettings().withFaultThreshold(3).faultThreshold)
    }

    @Test
    fun noInterruptFlagCanBeSwitched() {
        assertTrue(InterruptSettings().withNoInterrupt(true).noInterrupt)
    }

    @Test
    fun isImportantMatchesFormattedCallerNumbers() {
        val s = InterruptSettings().addNumber("5195550123", "Mom").settings
        assertTrue(s.isImportant("+1 (519) 555-0123".removePrefix("+1 ")))
        assertFalse(s.isImportant("5195550000"))
    }
}
