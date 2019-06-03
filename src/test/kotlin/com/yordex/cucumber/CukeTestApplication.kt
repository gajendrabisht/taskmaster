package com.yordex.cucumber

import com.yordex.taskmaster.Application
import com.yordex.taskmaster.repository.TaskRepository
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest(classes = [Application::class], webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner::class)
@ContextConfiguration
abstract class CukeTestApplication {

    @Autowired
    lateinit var taskRepository: TaskRepository

    @LocalServerPort
    private val port = 8080

    var restTemplate = TestRestTemplate()
//    public RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    fun baseUrl(): String {
        return String.format("http://localhost:%s", port)
    }
}