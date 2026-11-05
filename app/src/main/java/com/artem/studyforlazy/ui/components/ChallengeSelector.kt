package com.artem.studyforlazy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.artem.studyforlazy.model.AlarmChallengeType

@Composable
fun ChallengeSelector(
    selected: AlarmChallengeType,
    onSelect: (AlarmChallengeType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(Modifier.padding(vertical = 8.dp)) {
            AlarmChallengeType.entries.forEach { type ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(selected = selected == type, onClick = { onSelect(type) })
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(selected = selected == type, onClick = null)
                    Text(
                        text = type.label(),
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

private fun AlarmChallengeType.label(): String = when (this) {
    AlarmChallengeType.MATH -> "Решить пример"
    AlarmChallengeType.QR_CODE -> "Сканировать QR-код"
    AlarmChallengeType.PHOTO -> "Фото конспекта"
}
