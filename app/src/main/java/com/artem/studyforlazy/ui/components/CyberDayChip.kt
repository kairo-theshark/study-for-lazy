package com.artem.studyforlazy.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artem.studyforlazy.model.DayAttendanceState

private val ChipShape = CutCornerShape(topEnd = 10.dp, bottomStart = 4.dp)

@Composable
fun CyberDayChip(
    dayLabel: String,
    dateLabel: String,
    state: DayAttendanceState,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val accent = MaterialTheme.colorScheme.primary
    val danger = MaterialTheme.colorScheme.error
    val dim = MaterialTheme.colorScheme.surfaceVariant
    val onDim = MaterialTheme.colorScheme.onSurfaceVariant

    val fill = when (state) {
        DayAttendanceState.FUTURE -> Modifier.border(1.dp, accent, ChipShape)
        DayAttendanceState.NONE -> Modifier.background(dim)
        DayAttendanceState.FULL_ATTENDED -> Modifier.background(accent)
        DayAttendanceState.FULL_SKIPPED -> Modifier.background(danger)
        DayAttendanceState.PARTIAL_SKIPPED -> Modifier.drawBehind {
            drawRect(accent, size = Size(size.width, size.height / 2f))
            drawRect(danger, topLeft = Offset(0f, size.height / 2f), size = Size(size.width, size.height / 2f))
        }
    }

    // текст красим по половинам: на янтаре тёмный, на красном светлый
    val dayColor = when (state) {
        DayAttendanceState.FULL_ATTENDED, DayAttendanceState.PARTIAL_SKIPPED -> Color(0xFF1E1C1A)
        DayAttendanceState.FULL_SKIPPED -> Color(0xFFF6EFE9)
        DayAttendanceState.FUTURE -> accent
        DayAttendanceState.NONE -> onDim
    }
    val dateColor = when (state) {
        DayAttendanceState.FULL_ATTENDED -> Color(0xFF1E1C1A)
        DayAttendanceState.FULL_SKIPPED, DayAttendanceState.PARTIAL_SKIPPED -> Color(0xFFF6EFE9)
        DayAttendanceState.FUTURE -> accent
        DayAttendanceState.NONE -> onDim
    }

    Column(
        modifier = modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("▼", color = if (isSelected) accent else Color.Transparent, fontSize = 9.sp)
        Box(
            modifier = Modifier
                .size(width = 44.dp, height = 52.dp)
                .clip(ChipShape)
                .then(fill),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(dayLabel, style = MaterialTheme.typography.labelSmall, color = dayColor)
                Text(dateLabel, style = MaterialTheme.typography.titleMedium, color = dateColor)
            }
        }
        Text("▲", color = if (isSelected) accent else Color.Transparent, fontSize = 9.sp)
    }
}
