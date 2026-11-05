package com.artem.studyforlazy.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

@Composable
fun StudyForLazyTheme(content: @Composable () -> Unit) {
    // тёмная тема всегда: приложение для вечерних и утренних мучений
    MaterialTheme(colorScheme = darkColorScheme(), content = content)
}
