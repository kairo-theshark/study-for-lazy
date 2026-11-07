package com.artem.studyforlazy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.artem.studyforlazy.ui.theme.StudyForLazyTheme
import com.artem.studyforlazy.ui.navigation.BottomBar
import com.artem.studyforlazy.ui.navigation.Screen
import com.artem.studyforlazy.ui.screens.AlarmScreen
import com.artem.studyforlazy.ui.screens.DeadlinesScreen
import com.artem.studyforlazy.ui.screens.ScheduleScreen
import com.artem.studyforlazy.ui.screens.StatsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyForLazyTheme {
                StudyForLazyApp()
            }
        }
    }
}

@Composable
fun StudyForLazyApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            BottomBar(navController, backStackEntry?.destination)
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Schedule.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Screen.Schedule.route) { ScheduleScreen() }
            composable(Screen.Alarm.route) { AlarmScreen() }
            composable(Screen.Stats.route) { StatsScreen() }
            composable(Screen.Deadlines.route) { DeadlinesScreen() }
        }
    }
}
