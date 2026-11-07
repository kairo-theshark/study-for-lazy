package com.artem.studyforlazy.ui.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.artem.studyforlazy.data.local.AppDatabase
import com.artem.studyforlazy.data.local.entities.DeadlineEntity
import com.artem.studyforlazy.data.toModel
import com.artem.studyforlazy.model.DeadlineTask
import com.artem.studyforlazy.model.TaskPriority
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class DeadlinesViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = AppDatabase.getInstance(app).deadlineDao()

    val deadlines: StateFlow<List<DeadlineTask>> = dao.observeAll()
        .map { rows -> rows.map { it.toModel() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun toggleComplete(id: String) = viewModelScope.launch { dao.toggleCompleted(id) }

    fun delete(id: String) = viewModelScope.launch { dao.delete(id) }

    fun addDeadline(title: String, subject: String, dueDate: String, priority: TaskPriority) =
        viewModelScope.launch {
            dao.insertAll(listOf(DeadlineEntity(UUID.randomUUID().toString(), title, subject, dueDate, priority.name)))
        }
}
