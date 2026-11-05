package com.artem.studyforlazy.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artem.studyforlazy.model.Lesson
import com.artem.studyforlazy.ui.StudyForLazyTheme
import com.artem.studyforlazy.ui.components.LessonCard

private val mockLessons = listOf(
    Lesson("1", "Математический анализ", "09:00 – 10:30", "А-312", "Лекция"),
    Lesson("2", "Физика", "10:45 – 12:15", "Б-104", "Практика"),
    Lesson("3", "Программирование", "13:00 – 14:30", "К-201", "Лаборатория"),
    Lesson("4", "История", "14:45 – 16:15", "Г-18", "Семинар"),
)

@Composable
fun ScheduleScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text("Сегодня", style = MaterialTheme.typography.headlineSmall)
        }
        items(mockLessons, key = { it.id }) { lesson ->
            LessonCard(lesson)
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScheduleScreenPreview() {
    StudyForLazyTheme {
        ScheduleScreen()
    }
}
