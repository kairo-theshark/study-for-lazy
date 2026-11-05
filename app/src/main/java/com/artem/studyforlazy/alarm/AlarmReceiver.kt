package com.artem.studyforlazy.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // заглушка: здесь будет полноэкранный вызов и челлендж на отключение
        Log.d(TAG, "onReceive: будильник сработал, challenge=${intent.getStringExtra(EXTRA_CHALLENGE)}")
    }

    companion object {
        private const val TAG = "AlarmReceiver"
        const val EXTRA_CHALLENGE = "extra_challenge"
    }
}
