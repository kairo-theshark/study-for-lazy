package com.artem.studyforlazy.ui.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Warm Industrial Cyberpunk
private val Graphite = Color(0xFF1E1C1A)
private val GraphiteSurface = Color(0xFF282523)
private val Amber = Color(0xFFFF9E3B)
private val NeonMint = Color(0xFF4AF699)
private val CyberRed = Color(0xFFFF2A54)
private val Bone = Color(0xFFEDE6DF)

private val CyberColors = darkColorScheme(
    primary = Amber,
    onPrimary = Graphite,
    secondary = NeonMint,
    onSecondary = Color(0xFF10241A),
    error = CyberRed,
    onError = Color(0xFF2A0008),
    background = Graphite,
    onBackground = Bone,
    surface = GraphiteSurface,
    onSurface = Bone,
    surfaceVariant = Color(0xFF3A3632),
    onSurfaceVariant = Color(0xFFB9AFA5),
    outline = Color(0xFF5A524A),
)

// скошенные углы — карточки и чипы выглядят как шарды памяти
private val CyberShapes = Shapes(
    extraSmall = CutCornerShape(topEnd = 4.dp, bottomStart = 4.dp),
    small = CutCornerShape(topEnd = 6.dp, bottomStart = 4.dp),
    medium = CutCornerShape(topEnd = 10.dp, bottomStart = 4.dp),
    large = CutCornerShape(topEnd = 14.dp, bottomStart = 6.dp),
    extraLarge = CutCornerShape(topEnd = 18.dp, bottomStart = 8.dp),
)

@Composable
fun StudyForLazyTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = CyberColors, shapes = CyberShapes, content = content)
}
