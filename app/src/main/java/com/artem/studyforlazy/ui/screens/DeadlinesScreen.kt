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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.artem.studyforlazy.model.DeadlineTask
import com.artem.studyforlazy.model.TaskPriority
import com.artem.studyforlazy.ui.components.DeadlineCard
import com.artem.studyforlazy.ui.theme.StudyForLazyTheme
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable
fun DeadlinesScreen(
    modifier: Modifier = Modifier,
    viewModel: DeadlinesViewModel = viewModel(),
) {
    val tasks by viewModel.deadlines.collectAsState()
    DeadlinesContent(tasks = tasks, modifier = modifier)
}

@Composable
private fun DeadlinesContent(tasks: List<DeadlineTask>, modifier: Modifier = Modifier) {
    val today = remember { LocalDate.now() }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text("Дедлайны", style = MaterialTheme.typography.headlineSmall)
        }
        if (tasks.isEmpty()) {
            item {
                Text(
                    text = "Дедлайнов нет — подозрительно",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        items(tasks, key = { it.id }) { task ->
            DeadlineCard(
                task = task,
                daysLeft = ChronoUnit.DAYS.between(today, LocalDate.parse(task.dueDate)),
            )
        }
    }
}

private val previewTasks = listOf(
    DeadlineTask("1", "Курсовая: первая глава", "Экономика", "2026-11-09", TaskPriority.HIGH),
    DeadlineTask("2", "Лаба №3: сортировки", "Алгоритмы", "2026-11-14", TaskPriority.LOW),
)

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DeadlinesScreenPreview() {
    StudyForLazyTheme {
        DeadlinesContent(previewTasks)
    }
}
