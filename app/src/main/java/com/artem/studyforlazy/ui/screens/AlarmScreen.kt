package com.artem.studyforlazy.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artem.studyforlazy.model.AlarmConfig
import com.artem.studyforlazy.ui.theme.StudyForLazyTheme
import kotlin.math.roundToInt

@Composable
fun AlarmScreen(modifier: Modifier = Modifier) {
    var config by remember { mutableStateOf(AlarmConfig()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Карательный будильник", style = MaterialTheme.typography.headlineSmall)

        // --- Статус и время ---
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Будильник включён", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "Время подъёма: ${config.alarmTime}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    Switch(checked = config.isEnabled, onCheckedChange = { config = config.copy(isEnabled = it) })
                }
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                ) {
                    Text(
                        "⚠ Не позднее чем за ${config.minBufferMinutesBeforeFirstClass} мин до 1-й пары",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                    )
                }
            }
        }

        // --- Буфер до первой пары ---
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Запас до 1-й пары: ${config.minBufferMinutesBeforeFirstClass} мин", style = MaterialTheme.typography.titleMedium)
                Slider(
                    value = config.minBufferMinutesBeforeFirstClass.toFloat(),
                    onValueChange = { config = config.copy(minBufferMinutesBeforeFirstClass = (it / 15).roundToInt() * 15) },
                    valueRange = 30f..180f,
                    steps = 9, // (180-30)/15 - 1 = 9 шагов
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("30 мин", style = MaterialTheme.typography.labelSmall)
                    Text("180 мин", style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        // --- Протокол пробуждения (2 фазы) ---
        Text("Протокол пробуждения (2 фазы)", style = MaterialTheme.typography.titleMedium)

        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                    Text(
                        "Фаза 1 (обязательно): ${config.mathProblemsCount} математических примера",
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
                Text("Фаза 2 (случайный босс):", style = MaterialTheme.typography.titleSmall)
                BossCheckbox(
                    label = "Скан QR-кода в комнате",
                    checked = config.allowQrBoss,
                    onCheckedChange = {
                        if (!it && !config.allowObjectBoss) return@BossCheckbox
                        config = config.copy(allowQrBoss = it)
                    },
                )
                BossCheckbox(
                    label = "Поиск предмета камерой (чайник, кружка, раковина)",
                    checked = config.allowObjectBoss,
                    onCheckedChange = {
                        if (!it && !config.allowQrBoss) return@BossCheckbox
                        config = config.copy(allowObjectBoss = it)
                    },
                )
            }
        }

        // --- QR-коды ---
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Сгенерировать / Настроить QR-коды")
        }
    }
}

@Composable
private fun BossCheckbox(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(label, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(start = 4.dp))
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AlarmScreenPreview() {
    StudyForLazyTheme { AlarmScreen() }
}
