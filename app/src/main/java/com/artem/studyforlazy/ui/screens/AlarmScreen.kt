package com.artem.studyforlazy.ui.screens

import android.app.TimePickerDialog
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artem.studyforlazy.alarm.AndroidAlarmScheduler
import com.artem.studyforlazy.data.alarm.AlarmTimeCalculator
import com.artem.studyforlazy.data.local.AppDatabase
import com.artem.studyforlazy.model.AlarmConfig
import com.artem.studyforlazy.ui.theme.StudyForLazyTheme
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun AlarmScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var config by remember { mutableStateOf(AlarmConfig()) }
    var limitWarning by remember { mutableStateOf<String?>(null) }

    val scheduler = remember { AndroidAlarmScheduler(context) }
    val calculator = remember {
        AlarmTimeCalculator(AppDatabase.getInstance(context).scheduleDao())
    }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Карательный будильник", style = MaterialTheme.typography.headlineSmall)

        // --- Статус и время ---
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        Modifier.weight(1f).clickable {
                            showTimePicker(context, config) { picked ->
                                scope.launch {
                                    val limit = calculator.latestAllowedWakeUp(config.minBufferMinutesBeforeFirstClass)
                                    if (limit != null && picked.isAfter(limit)) {
                                        limitWarning = "⚠ Слишком поздно! Сброс на ${limit.fmt()}"
                                        config = config.copy(alarmTime = limit.fmt())
                                    } else {
                                        limitWarning = null
                                        config = config.copy(alarmTime = picked.fmt())
                                    }
                                    if (config.isEnabled) scheduler.schedule(LocalTime.parse(config.alarmTime, DateTimeFormatter.ofPattern("HH:mm")))
                                }
                            }
                        },
                    ) {
                        Text("Время подъёма", style = MaterialTheme.typography.titleMedium)
                        Text(config.alarmTime, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
                        Text("(нажми, чтобы изменить)", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(
                        checked = config.isEnabled,
                        onCheckedChange = { enabled ->
                            config = config.copy(isEnabled = enabled)
                            if (enabled) scheduler.schedule(LocalTime.parse(config.alarmTime, DateTimeFormatter.ofPattern("HH:mm")))
                            else scheduler.cancel()
                        },
                    )
                }
                limitWarning?.let { warn ->
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                        Text(warn, Modifier.padding(10.dp), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onErrorContainer)
                    }
                }
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                    Text(
                        "⚠ Не позднее чем за ${config.minBufferMinutesBeforeFirstClass} мин до 1-й пары",
                        Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
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
                    valueRange = 30f..180f, steps = 9,
                )
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("30 мин", style = MaterialTheme.typography.labelSmall)
                    Text("180 мин", style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        // --- Протокол пробуждения ---
        Text("Протокол пробуждения (2 фазы)", style = MaterialTheme.typography.titleMedium)
        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                    Text("Фаза 1 (обязательно): ${config.mathProblemsCount} математических примера", Modifier.padding(12.dp), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
                Text("Фаза 2 (случайный босс):", style = MaterialTheme.typography.titleSmall)
                BossCheckbox("Скан QR-кода в комнате", config.allowQrBoss) {
                    if (!it && !config.allowObjectBoss) return@BossCheckbox
                    config = config.copy(allowQrBoss = it)
                }
                BossCheckbox("Поиск предмета камерой (чайник, кружка, раковина)", config.allowObjectBoss) {
                    if (!it && !config.allowQrBoss) return@BossCheckbox
                    config = config.copy(allowObjectBoss = it)
                }
            }
        }

        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Сгенерировать / Настроить QR-коды")
        }
    }
}

private fun showTimePicker(context: Context, config: AlarmConfig, onPicked: (LocalTime) -> Unit) {
    val parts = config.alarmTime.split(":")
    val h = parts[0].toIntOrNull() ?: 7
    val m = parts[1].toIntOrNull() ?: 30
    TimePickerDialog(context, { _, hour, minute -> onPicked(LocalTime.of(hour, minute)) }, h, m, true).show()
}

private fun LocalTime.fmt(): String = String.format(java.util.Locale.ROOT, "%02d:%02d", hour, minute)

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
