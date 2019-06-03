package com.yordex.taskmaster.service

import com.yordex.taskmaster.entity.Task
import com.yordex.taskmaster.poko.TaskRequest
import java.util.UUID

interface TaskService {

    fun get(taskId: UUID): Task

    fun getAll(): List<Task>

    fun add(taskRequest: TaskRequest): Task

    fun update(taskId: UUID, taskRequest: TaskRequest): Task

    fun delete(taskId: UUID)

}