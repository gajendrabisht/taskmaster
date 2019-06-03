package com.yordex.taskmaster.controller

import com.yordex.taskmaster.entity.Task
import com.yordex.taskmaster.poko.TaskRequest
import com.yordex.taskmaster.service.TaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(path = ["/tasks"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [APPLICATION_JSON_VALUE])
class TaskController(@Autowired val taskService: TaskService) {

    @GetMapping(path = ["/{taskId}"])
    fun get(@PathVariable taskId: UUID): Task {
        return taskService.get(taskId);
    }

    @GetMapping
    fun getAll(): List<Task> {
        return taskService.getAll()
    }

    @PostMapping
    fun add(@RequestBody taskRequest: TaskRequest): ResponseEntity<Task> {
        return ResponseEntity(taskService.add(taskRequest), HttpStatus.CREATED)
    }

    @PutMapping(path = ["/{taskId}"])
    fun update(@PathVariable taskId: UUID, @RequestBody taskRequest: TaskRequest): Task {
        return taskService.update(taskId, taskRequest);
    }

    @DeleteMapping(path = ["/{taskId}"])
    fun delete(@PathVariable taskId: UUID): ResponseEntity<String> {
        taskService.delete(taskId)
        return ResponseEntity.ok().build()
    }

}