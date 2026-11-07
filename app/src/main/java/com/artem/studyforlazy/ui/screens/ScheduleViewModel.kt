package com.artem.studyforlazy.ui.screens

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.artem.studyforlazy.data.local.AppDatabase
import com.artem.studyforlazy.data.toModel
import com.artem.studyforlazy.model.Lesson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.temporal.WeekFields

private data class DayQuery(val dayOfWeek: Int, val isOddWeek: Boolean)

@OptIn(ExperimentalCoroutinesApi::class)
class ScheduleViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = AppDatabase.getInstance(app).scheduleDao()
    private val today = LocalDate.now()
    private val query = MutableStateFlow(DayQuery(today.dayOfWeek.value, isOddIsoWeek(today)))

    var selectedDayOfWeek by mutableStateOf(query.value.dayOfWeek)
        private set

    var isOddWeek by mutableStateOf(query.value.isOddWeek)
        private set

    val lessons: StateFlow<List<Lesson>> = query
        .flatMapLatest { dao.observeLessons(it.dayOfWeek, it.isOddWeek) }
        .map { rows -> rows.map { it.toModel() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun selectDay(dayOfWeek: Int) {
        selectedDayOfWeek = dayOfWeek
        query.value = DayQuery(dayOfWeek, isOddWeek)
    }

    fun toggleWeek() {
        isOddWeek = !isOddWeek
        query.value = DayQuery(selectedDayOfWeek, isOddWeek)
    }
}

private fun isOddIsoWeek(date: LocalDate): Boolean =
    date.get(WeekFields.ISO.weekOfWeekBasedYear()) % 2 != 0
