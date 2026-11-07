package com.artem.studyforlazy.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import com.artem.studyforlazy.model.AlarmChallengeType
import com.artem.studyforlazy.model.AlarmConfig
import com.artem.studyforlazy.ui.theme.StudyForLazyTheme
import com.artem.studyforlazy.ui.components.ChallengeSelector

@Composable
fun AlarmScreen(modifier: Modifier = Modifier) {
    var config by remember { mutableStateOf(AlarmConfig(true, AlarmChallengeType.MATH)) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Будильник-садист", style = MaterialTheme.typography.headlineSmall)

        Card {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Будильник включён", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "Выключить можно только пройдя челлендж",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                Switch(
                    checked = config.isEnabled,
                    onCheckedChange = { config = config.copy(isEnabled = it) },
                )
            }
        }

        Text("Челлендж для отключения", style = MaterialTheme.typography.titleMedium)

        ChallengeSelector(
            selected = config.challengeType,
            onSelect = { config = config.copy(challengeType = it) },
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AlarmScreenPreview() {
    StudyForLazyTheme {
        AlarmScreen()
    }
}
