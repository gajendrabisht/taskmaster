package com.yordex.taskmaster.service

import com.yordex.taskmaster.TaskNotFoundException
import com.yordex.taskmaster.controller.TaskController
import com.yordex.taskmaster.entity.Task
import com.yordex.taskmaster.poko.TaskRequest
import com.yordex.taskmaster.repository.TaskRepository
import com.yordex.taskmaster.service.TaskServiceImpl
import spock.lang.Specification

class TaskServiceTest extends Specification {

    TaskRepository taskRepository
    TaskService taskService

    def setup() {
        taskRepository = Mock(TaskRepository)
        taskService = new TaskServiceImpl(taskRepository)
    }

    def "get a task"() {
        given:
        def taskId = UUID.randomUUID()
        def expectedTask = new Task(taskId, "someTitle", "someDescription")
        taskRepository.findById(taskId) >> Optional.of(expectedTask)

        when:
        def actualTask = taskService.get(taskId)

        then:
        actualTask == expectedTask
    }

    def "get all tasks"() {
        given:
        def taskId_1 = UUID.randomUUID()
        def taskId_2 = UUID.randomUUID()
        def expectedTasks = Arrays.asList(
                new Task(taskId_1, "someTitle", "someDescription"),
                new Task(taskId_2, "someTitle", "someDescription")
        )
        taskRepository.findAll() >> expectedTasks

        when:
        def actualTasks = taskService.getAll()

        then:
        actualTasks == expectedTasks
    }

    def "delete a task"() {
        given:
        def taskId = UUID.randomUUID()
        def task = new Task(taskId, "someTitle", "someDescription")
        taskRepository.findById(taskId) >> Optional.of(task)

        when:
        taskService.delete(taskId)

        then:
        1 * taskRepository.delete(task)
    }

    def "get task - not found"() {
        given:
        def taskId = UUID.randomUUID()
        taskRepository.findById(taskId) >> Optional.empty()

        when:
        taskService.get(taskId)

        then:
        def e = thrown(TaskNotFoundException)
        e.message == "Task: ${taskId.toString()} Not Found"
    }

}
