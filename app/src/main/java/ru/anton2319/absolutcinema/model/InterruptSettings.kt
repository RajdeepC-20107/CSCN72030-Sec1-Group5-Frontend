package ru.anton2319.absolutcinema.model

data class ImportantNumber(val number: String, val label: String)

/** The interrupt settings shown on the Interrupt Mode screen (see docs/api-contract.md). */
data class InterruptSettings(
    val noInterrupt: Boolean = false,
    val importantNumbers: List<ImportantNumber> = emptyList(),
    val faultThreshold: Int = 2
) {
    companion object {
        const val MIN_THRESHOLD = 1
        const val MAX_THRESHOLD = 10
        const val MIN_DIGITS = 7
        const val MAX_DIGITS = 15
    }
}

/** The result of trying to add a number: the new settings, or an error message to show. */
data class AddNumberResult(val settings: InterruptSettings, val error: String? = null)

/** Keeps only the digits, so "(519) 555-0123" and "5195550123" are the same number. */
fun normalizeNumber(text: String): String = text.filter { it.isDigit() }

fun InterruptSettings.withNoInterrupt(on: Boolean): InterruptSettings = copy(noInterrupt = on)

fun InterruptSettings.withFaultThreshold(value: Int): InterruptSettings =
    copy(faultThreshold = value.coerceIn(InterruptSettings.MIN_THRESHOLD, InterruptSettings.MAX_THRESHOLD))

fun InterruptSettings.addNumber(rawNumber: String, rawLabel: String): AddNumberResult {
    val digits = normalizeNumber(rawNumber)
    if (digits.length < InterruptSettings.MIN_DIGITS || digits.length > InterruptSettings.MAX_DIGITS) {
        return AddNumberResult(
            this,
            "Enter a phone number with ${InterruptSettings.MIN_DIGITS} to ${InterruptSettings.MAX_DIGITS} digits"
        )
    }
    if (importantNumbers.any { it.number == digits }) {
        return AddNumberResult(this, "That number is already in the list")
    }
    val label = rawLabel.trim().ifEmpty { digits }
    return AddNumberResult(copy(importantNumbers = importantNumbers + ImportantNumber(digits, label)))
}

fun InterruptSettings.removeNumber(number: String): InterruptSettings =
    copy(importantNumbers = importantNumbers.filterNot { it.number == number })

/** True when a call from this number should start an interrupt (when No Interrupt is off). */
fun InterruptSettings.isImportant(caller: String): Boolean =
    importantNumbers.any { it.number == normalizeNumber(caller) }
