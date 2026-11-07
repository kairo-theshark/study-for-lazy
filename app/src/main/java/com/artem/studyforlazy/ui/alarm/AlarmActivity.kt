package com.artem.studyforlazy.ui.alarm

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.artem.studyforlazy.alarm.AlarmService
import com.artem.studyforlazy.alarm.MathChallenge
import com.artem.studyforlazy.alarm.MathProblem

class AlarmActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Показываем поверх экрана блокировки
        setShowWhenLocked(true)
        setTurnScreenOn(true)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Нельзя закрыть свайпом назад
        onBackPressedDispatcher.addCallback(this) { /* заблокировано */ }

        setContent {
            AlarmScreen(onSolved = ::dismissAlarm)
        }
    }

    private fun dismissAlarm() {
        stopService(Intent(this, AlarmService::class.java))
        finish()
    }
}

@Composable
private fun AlarmScreen(onSolved: () -> Unit) {
    val problems = remember { MathChallenge.generate(2) }
    val answers = remember { mutableStateListOf("", "") }
    val allCorrect = problems.indices.all { i ->
        answers[i].trim().toIntOrNull() == problems[i].answer
    }

    val alpha by rememberInfiniteTransition(label = "blink").animateFloat(
        initialValue = 1f, targetValue = 0.2f, label = "alpha",
        animationSpec = infiniteRepeatable(tween(600), RepeatMode.Reverse),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "⚡ ПОДЪЁМ НА ПАРЫ ⚡",
            fontSize = 28.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFFFF1744),
            modifier = Modifier.alpha(alpha),
        )
        Spacer(Modifier.height(32.dp))
        Text("Фаза 1: реши примеры", color = Color(0xFFFF6D00), fontSize = 16.sp)
        Spacer(Modifier.height(16.dp))

        problems.forEachIndexed { i, problem ->
            ProblemField(problem = problem, value = answers[i], onValueChange = { answers[i] = it })
            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { if (allCorrect) onSolved() },
            enabled = allCorrect,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF1744)),
        ) {
            Text("ОТКЛЮЧИТЬ СИРЕНУ", fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
private fun ProblemField(problem: MathProblem, value: String, onValueChange: (String) -> Unit) {
    val correct = value.trim().toIntOrNull() == problem.answer
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(problem.expression, color = Color(0xFFCFCFCF)) },
        isError = value.isNotEmpty() && !correct,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
    )
}
