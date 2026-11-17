package ru.anton2319.absolutcinema.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import ru.anton2319.absolutcinema.model.RoomLayout

@Composable
fun RoomMap(layout: RoomLayout, modifier: Modifier = Modifier) {
    val outline = MaterialTheme.colorScheme.outline
    val speakerColor = MaterialTheme.colorScheme.onSurface
    val seatColor = MaterialTheme.colorScheme.primary
    val shape = MaterialTheme.shapes.large

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.1f)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(1.dp, outline, shape)
            .semantics { contentDescription = "Room plan with ${layout.speakers.size} speakers and your seat" },
    ) {
        val dash = PathEffect.dashPathEffect(floatArrayOf(8.dp.toPx(), 8.dp.toPx()))
        drawLine(outline, Offset(size.width / 2, 0f), Offset(size.width / 2, size.height), strokeWidth = 1.dp.toPx(), pathEffect = dash)
        drawLine(outline, Offset(0f, size.height / 2), Offset(size.width, size.height / 2), strokeWidth = 1.dp.toPx(), pathEffect = dash)

        val w = 36.dp.toPx()
        val h = 56.dp.toPx()
        layout.speakers.forEach { speaker ->
            val c = Offset(speaker.x * size.width, speaker.y * size.height)
            rotate(degrees = speaker.rotationDegrees, pivot = c) {
                drawRoundRect(
                    color = speakerColor,
                    topLeft = Offset(c.x - w / 2, c.y - h / 2),
                    size = Size(w, h),
                    cornerRadius = CornerRadius(8.dp.toPx()),
                    style = Stroke(2.dp.toPx()),
                )
                drawCircle(speakerColor, radius = 4.dp.toPx(), center = Offset(c.x, c.y - h * 0.26f), style = Stroke(1.5.dp.toPx()))
                drawCircle(speakerColor, radius = 9.dp.toPx(), center = Offset(c.x, c.y + h * 0.16f), style = Stroke(1.5.dp.toPx()))
            }
        }

        val seat = Offset(layout.seatX * size.width, layout.seatY * size.height)
        drawCircle(seatColor, radius = 11.dp.toPx(), center = seat, style = Stroke(2.dp.toPx()))
        drawCircle(seatColor, radius = 3.dp.toPx(), center = seat)
    }
}
