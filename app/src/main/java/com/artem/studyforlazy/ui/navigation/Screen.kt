package com.artem.studyforlazy.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.graphics.vector.ImageVector

// иконки только из material-icons-core: extended не подключён
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Schedule : Screen("schedule", "Расписание", Icons.Filled.DateRange)
    data object Alarm : Screen("alarm", "Будильник-садист", Icons.Filled.Notifications)
    data object Stats : Screen("stats", "Статистика проёбов", Icons.AutoMirrored.Filled.List)

    companion object {
        val tabs = listOf(Schedule, Alarm, Stats)
    }
}
