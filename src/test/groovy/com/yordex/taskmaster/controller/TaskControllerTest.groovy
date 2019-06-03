package com.yordex.taskmaster.controller

import com.yordex.taskmaster.controller.TaskController
import com.yordex.taskmaster.entity.Task
import com.yordex.taskmaster.TaskNotFoundException
import com.yordex.taskmaster.poko.TaskRequest
import com.yordex.taskmaster.service.TaskServiceImpl
import spock.lang.Specification

class TaskControllerTest extends Specification {

    def taskService
    def taskController

    def setup() {
        taskService = Mock(TaskServiceImpl)
        taskController = new TaskController(taskService)
    }

    def "get a task"() {
        given:
        def taskId = UUID.randomUUID()
        def expectedTask = new Task(taskId, "someTitle", "someDescription")
        taskService.get(taskId) >> expectedTask

        when:
        def actualTask = taskController.get(taskId)

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
        taskService.getAll() >> expectedTasks

        when:
        def actualTasks = taskController.getAll()

        then:
        actualTasks == expectedTasks
    }

    def "add a task"() {
        given:
        TaskRequest taskRequest = new TaskRequest("someTitle", "someDescription")

        when:
        taskController.add(taskRequest)

        then:
        1 * taskService.add(taskRequest)
    }

    def "update a task"() {
        given:
        def taskId = UUID.randomUUID()
        def taskRequest = new TaskRequest("newTitle", "newDescription")
        def updatedTask = new Task(taskId, "newTitle", "newDescription")
        taskService.update(taskId, taskRequest) >> updatedTask

        when:
        def actualUpdatedTask = taskController.update(taskId, taskRequest)

        then:
        actualUpdatedTask == updatedTask
    }

    def "delete a task"() {
        given:
        def taskId = UUID.randomUUID()

        when:
        taskController.delete(taskId)

        then:
        1 * taskService.delete(taskId)
    }

    def "get task - not found"() {
        given:
        def taskId = UUID.randomUUID()
        taskService.get(taskId) >> { throw new TaskNotFoundException(taskId) }

        when:
        taskController.get(taskId)

        then:
        def e = thrown(TaskNotFoundException)
        e.message == "Task: ${taskId.toString()} Not Found"
    }

    def "update task - not found"() {
        given:
        def taskId = UUID.randomUUID()
        def taskRequest = new TaskRequest("newTitle", "newDescription")
        taskService.update(taskId, taskRequest) >> { throw new TaskNotFoundException(taskId) }

        when:
        taskController.update(taskId, taskRequest)

        then:
        def e = thrown(TaskNotFoundException)
        e.message == "Task: ${taskId.toString()} Not Found"
    }

    def "delete task - not found"() {
        given:
        def taskId = UUID.randomUUID()
        taskService.delete(taskId) >> { throw new TaskNotFoundException(taskId) }

        when:
        taskController.delete(taskId)

        then:
        def e = thrown(TaskNotFoundException)
        e.message == "Task: ${taskId.toString()} Not Found"
    }

}
