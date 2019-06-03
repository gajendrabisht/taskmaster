package com.yordex.cucumber

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.yordex.taskmaster.entity.Task
import cucumber.api.java8.En
import io.cucumber.datatable.DataTable
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.DELETE
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.http.HttpMethod.PUT
import org.springframework.http.ResponseEntity
import java.util.UUID

class CukeStepDefs : CukeTestApplication(), En {

    init {

        val world = CukeWorld()
        val headers = HttpHeaders()
        headers.set("Content-Type", "application/json")
        headers.set("Accept", "application/json")
        val entityWithoutBody: HttpEntity<String> = HttpEntity("empty", headers)

        Before { _ ->
            cleanupDB()
        }

        After { _ ->
            cleanupDB()
        }

        Given("^tasks exist$") { table: DataTable ->
            table.asMaps().forEach { row ->
                taskRepository.save(Task(UUID.fromString(row["id"].orEmpty()), row["title"].orEmpty(), row["description"].orEmpty()))
            }
        }

        When("^I add a task$") { table: DataTable ->
            val httpEntity = HttpEntity(table.asMaps()[0], headers)
            val response = restTemplate.exchange("${baseUrl()}/tasks", POST, httpEntity, String::class.java)
            setResponseInWorld(world, response)
        }

        When("^I update task '(.*)'$") { taskId: String, table: DataTable ->
            val httpEntity = HttpEntity(table.asMaps()[0], headers)
            val response = restTemplate.exchange("${baseUrl()}/tasks/$taskId", PUT, httpEntity, String::class.java)
            setResponseInWorld(world, response)
        }

        When("^I delete task '(.*)'$") { taskId: String ->
            val response = restTemplate.exchange("${baseUrl()}/tasks/$taskId", DELETE, entityWithoutBody, String::class.java)
            setResponseInWorld(world, response)
        }

        When("^I request task id '(.*)'$") { taskId: String ->
            val response = restTemplate.exchange("${baseUrl()}/tasks/$taskId", GET, entityWithoutBody, String::class.java)
            setResponseInWorld(world, response)
        }

        When("^I request all tasks$") {
            val response = restTemplate.exchange("${baseUrl()}/tasks", GET, entityWithoutBody, String::class.java)
            setResponseInWorld(world, response)
        }

        Then("^I should get response status (\\d+)$") { statusCode: Int ->
            assertThat(world.responseStatus, `is`(statusCode))
        }

        Then("^I should get newly created pass as$") { table: DataTable ->
            val expectedTask = table.asMaps()[0]
            val actualTask = jacksonObjectMapper().readValue(world.responseBody, Task::class.java)
            assertNotNull(actualTask.id)
            assertThat(actualTask.title, `is`(expectedTask["title"]))
            assertThat(actualTask.description, `is`(expectedTask["description"]))
        }

        Then("^I should get task returned as$") { table: DataTable ->
            val expectedTask = table.asMaps()[0]
            val actualTask = jacksonObjectMapper().readValue(world.responseBody, Task::class.java)
            assertThat(actualTask.id.toString(), `is`(expectedTask["id"]))
            assertThat(actualTask.title, `is`(expectedTask["title"]))
            assertThat(actualTask.description, `is`(expectedTask["description"]))
        }

        Then("^I should get tasks returned as$") { table: DataTable ->
            val actualTasks = jacksonObjectMapper().readValue<List<Task>>(world.responseBody.orEmpty())
            table.asMaps().forEach { expectedTask ->
                val actualTask = actualTasks.find { it.id.toString() == expectedTask["id"] }
                assertThat(actualTask?.title, `is`(expectedTask["title"]))
                assertThat(actualTask?.description, `is`(expectedTask["description"]))
            }

        }

        Then("^task exists in database$") { table: DataTable ->
            val expectedTask = table.asMaps()[0]
            val taskInDB: Task = when (expectedTask.containsKey("id")) {
                true -> taskRepository.findById(UUID.fromString(expectedTask["id"])).get()
                false -> taskRepository.findAll().first()
            }
            assertNotNull(taskInDB.id)
            assertThat(taskInDB.title, `is`(expectedTask["title"]))
            assertThat(taskInDB.description, `is`(expectedTask["description"]))
        }

        Then("^no tasks exist in database$") {
            assertThat(taskRepository.count(), `is`(0L))
        }

        Then("^I should get error message '(.*)'$") { errorMessage: String ->
            assertThat(world.responseBody, `is`(errorMessage));
        }

    }

    private fun setResponseInWorld(world: CukeWorld, response: ResponseEntity<String>) {
        world.responseStatus = response.statusCodeValue
        world.responseBody = response.body
    }

    private fun cleanupDB() {
        taskRepository.deleteAll()
    }
}