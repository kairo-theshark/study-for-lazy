package com.artem.studyforlazy.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.artem.studyforlazy.model.DayAttendanceState

private data class DayChip(val label: String, val date: String, val state: DayAttendanceState)

private val mockWeek = listOf(
    DayChip("ПН", "03", DayAttendanceState.FULL_ATTENDED),
    DayChip("ВТ", "04", DayAttendanceState.PARTIAL_SKIPPED),
    DayChip("СР", "05", DayAttendanceState.FULL_SKIPPED),
    DayChip("ЧТ", "06", DayAttendanceState.FULL_ATTENDED),
    DayChip("ПТ", "07", DayAttendanceState.NONE),
    DayChip("СБ", "08", DayAttendanceState.FUTURE),
    DayChip("ВС", "09", DayAttendanceState.FUTURE),
)

@Composable
fun WeekStrip(
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(mockWeek) { index, day ->
            CyberDayChip(
                dayLabel = day.label,
                dateLabel = day.date,
                state = day.state,
                isSelected = index == selectedIndex,
                onClick = { onSelect(index) },
            )
        }
    }
}
