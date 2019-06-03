package com.yordex.taskmaster.repository

import com.yordex.taskmaster.entity.Task
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TaskRepository : CrudRepository<Task, UUID> {
}