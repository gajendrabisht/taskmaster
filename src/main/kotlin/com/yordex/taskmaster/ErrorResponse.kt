package com.yordex.taskmaster

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
class ErrorResponse<T>(errors: List<T>) {

    var errors: List<T> = ArrayList(1)

    init {
        this.errors = errors
    }

}