package com.artem.studyforlazy.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.artem.studyforlazy.model.Lesson
import com.artem.studyforlazy.ui.components.LessonCard
import com.artem.studyforlazy.ui.components.WeekStrip
import com.artem.studyforlazy.ui.theme.StudyForLazyTheme

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel = viewModel(),
) {
    val lessons by viewModel.lessons.collectAsState()
    ScheduleContent(
        lessons = lessons,
        selectedDay = viewModel.selectedDayOfWeek,
        onSelectDay = viewModel::selectDay,
        modifier = modifier,
    )
}

@Composable
private fun ScheduleContent(
    lessons: List<Lesson>,
    selectedDay: Int,
    onSelectDay: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxSize()) {
        WeekStrip(selectedIndex = selectedDay - 1, onSelect = { onSelectDay(it + 1) })

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Text("Сегодня", style = MaterialTheme.typography.headlineSmall)
            }
            if (lessons.isEmpty()) {
                item {
                    Text(
                        text = "Пар нет — отдыхай",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
            items(lessons, key = { it.id }) { lesson ->
                LessonCard(lesson)
            }
        }
    }
}

private val previewLessons = listOf(
    Lesson("1", "Математический анализ", "Лекция", "А-312", "09:00", "10:30", 5, false, "voenmeh_main"),
    Lesson("2", "Физика", "Практика", "Б-104", "10:45", "12:15", 5, false, "voenmeh_main"),
)

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScheduleScreenPreview() {
    StudyForLazyTheme {
        ScheduleContent(previewLessons, selectedDay = 5, onSelectDay = {})
    }
}
