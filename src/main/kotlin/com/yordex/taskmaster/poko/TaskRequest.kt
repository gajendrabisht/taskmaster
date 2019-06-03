package com.yordex.taskmaster.poko

import com.yordex.taskmaster.entity.Task
import java.util.UUID

data class TaskRequest(
        val title: String,
        val description: String
) {
    fun toEntity(taskId: UUID): Task {
        return Task(taskId, title, description)
    }
}