package com.artem.studyforlazy.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artem.studyforlazy.model.AttendanceStats
import com.artem.studyforlazy.ui.theme.StudyForLazyTheme

@Composable
fun StatsScreen(modifier: Modifier = Modifier) {
    val stats = AttendanceStats(totalClasses = 24, attendedClasses = 17, skippedClasses = 7)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text("Статистика проёбов", style = MaterialTheme.typography.headlineSmall)
        StatCard(label = "Посещено пар", value = stats.attendedClasses.toString())
        StatCard(label = "Пропущено пар", value = stats.skippedClasses.toString())
        StatCard(label = "Явка", value = "${stats.attendancePercent}%")
    }
}

@Composable
private fun StatCard(label: String, value: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.bodyMedium)
            Text(value, style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun StatsScreenPreview() {
    StudyForLazyTheme {
        StatsScreen()
    }
}
