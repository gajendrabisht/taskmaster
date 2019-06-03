package com.yordex.taskmaster

import java.util.UUID

class TaskNotFoundException(taskId: UUID) : RuntimeException("Task: ${taskId.toString()} Not Found")