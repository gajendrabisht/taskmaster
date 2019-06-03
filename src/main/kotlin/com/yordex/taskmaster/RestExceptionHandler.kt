package com.yordex.taskmaster

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.Arrays
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
/**
 * Override rest exception handling eg. ConstraintViolationException should not result in 500
 */
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMissingServletRequestParameter(ex: MissingServletRequestParameterException, headers: HttpHeaders,
                                                      status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val error = "${ex.parameterName} parameter is missing."
        return ResponseEntity(ErrorResponse(Arrays.asList(error)), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    protected fun handleConstraintViolationException(ex: ConstraintViolationException, request: HttpServletRequest): ResponseEntity<*> {
        return try {
            val messages = ex.constraintViolations.stream().map { "${it.propertyPath}: ${it.message}" }.collect(Collectors.toList())
            ResponseEntity(ErrorResponse(messages), HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity(ErrorResponse(Arrays.asList<String>(ex.message)), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @ExceptionHandler(TaskNotFoundException::class)
    protected fun taskNotFoundException(taskNotFoundException: TaskNotFoundException): ResponseEntity<*> {
        return status(HttpStatus.NOT_FOUND).body(ErrorResponse(Arrays.asList(taskNotFoundException.message)))
    }

}