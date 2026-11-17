package ru.anton2319.absolutcinema.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

val Ink = Color(0xFF121214)
val Panel = Color(0xFF1A1A1E)
val Hairline = Color(0xFF4A4A52)
val HairlineSoft = Color(0xFF2E2E34)
val Chalk = Color(0xFFECEAE4)
val ChalkDim = Color(0xFF9B9AA3)
val LinkBlue = Color(0xFF6CB2FF)
val OnGreen = Color(0xFF5FCB8E)
val OffOrange = Color(0xFFE9923B)

private val CinemaColors = darkColorScheme(
    primary = LinkBlue,
    onPrimary = Ink,
    secondary = OnGreen,
    onSecondary = Ink,
    tertiary = OffOrange,
    onTertiary = Ink,
    background = Ink,
    onBackground = Chalk,
    surface = Ink,
    onSurface = Chalk,
    surfaceVariant = Panel,
    onSurfaceVariant = ChalkDim,
    outline = Hairline,
    outlineVariant = HairlineSoft,
)

private val base = Typography()
private val CinemaTypography = base.copy(
    headlineSmall = base.headlineSmall.copy(fontWeight = FontWeight.Bold),
    titleLarge = base.titleLarge.copy(fontWeight = FontWeight.Bold),
    titleMedium = base.titleMedium.copy(fontWeight = FontWeight.SemiBold),
    labelLarge = base.labelLarge.copy(fontWeight = FontWeight.SemiBold),
)

private val CinemaShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(14.dp),
    large = RoundedCornerShape(22.dp),
)

@Composable
fun AbsolutCinemaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CinemaColors,
        typography = CinemaTypography,
        shapes = CinemaShapes,
        content = content,
    )
}
