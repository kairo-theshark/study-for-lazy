package com.artem.studyforlazy.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

// иконки только из material-icons-core: extended не подключён
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Schedule : Screen("schedule", "Расписание", Icons.Filled.DateRange)
    data object Alarm : Screen("alarm", "Будильник", Icons.Filled.Notifications)
    data object Stats : Screen("stats", "Статистика", Icons.AutoMirrored.Filled.List)
    data object Deadlines : Screen("deadlines", "Дедлайны", Icons.Filled.Warning)

    companion object {
        val tabs = listOf(Schedule, Alarm, Stats, Deadlines)
    }
}
