package com.artem.studyforlazy.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.artem.studyforlazy.model.DeadlineTask
import com.artem.studyforlazy.model.TaskPriority

@Composable
fun DeadlineCard(task: DeadlineTask, daysLeft: Long, modifier: Modifier = Modifier) {
    val accent = task.priority.color()

    Card(modifier = modifier.fillMaxWidth(), border = BorderStroke(1.dp, accent)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f),
                )
                Text(daysLeft.label(), style = MaterialTheme.typography.labelMedium, color = accent)
            }
            Text(
                text = "${task.subject} · ${task.dueDate}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Surface(color = accent.copy(alpha = 0.15f), shape = MaterialTheme.shapes.small) {
                Text(
                    text = task.priority.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = accent,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                )
            }
        }
    }
}

fun TaskPriority.color() = when (this) {
    TaskPriority.CRITICAL -> Color(0xFFFF2A54)
    TaskPriority.HIGH -> Color(0xFFFF9E3B)
    TaskPriority.MEDIUM -> Color(0xFF4AF699)
    TaskPriority.LOW -> Color(0xFFB9AFA5)
}

private fun Long.label(): String = when {
    this < 0 -> "просрочено на ${-this} дн."
    this == 0L -> "сегодня"
    this == 1L -> "завтра"
    else -> "через $this дн."
}
