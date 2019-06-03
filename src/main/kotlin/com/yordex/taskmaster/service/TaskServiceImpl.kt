package com.yordex.taskmaster.service

import com.yordex.taskmaster.entity.Task
import com.yordex.taskmaster.TaskNotFoundException
import com.yordex.taskmaster.poko.TaskRequest
import com.yordex.taskmaster.repository.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
open class TaskServiceImpl(@Autowired val taskRepository: TaskRepository) : TaskService {

    override fun get(taskId: UUID): Task {
        return taskRepository.findById(taskId).orElseThrow { TaskNotFoundException(taskId) }
    }

    override fun getAll(): List<Task> {
        return taskRepository.findAll().toList()
    }

    override fun add(taskRequest: TaskRequest): Task {
        return taskRepository.save(taskRequest.toEntity(UUID.randomUUID()))
    }

    override fun update(taskId: UUID, taskRequest: TaskRequest): Task {
        get(taskId) // to check that task exists
        return taskRepository.save(taskRequest.toEntity(taskId))
    }

    override fun delete(taskId: UUID) {
        taskRepository.delete(get(taskId))
    }

}