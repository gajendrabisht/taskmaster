package com.yordex.taskmaster.entity

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Task(
        @Id
        val id: UUID = UUID.randomUUID(),
        val title: String,
        val description: String
)