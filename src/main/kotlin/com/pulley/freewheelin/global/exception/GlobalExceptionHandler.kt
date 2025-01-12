package com.pulley.freewheelin.global.exception

import jakarta.validation.ConstraintViolationException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.to

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception): ResponseEntity<Map<String, String?>> {
        val errors = mapOf(
            "errorMessage" to ex.localizedMessage,
            "statusCode" to HttpStatus.INTERNAL_SERVER_ERROR.name
        )

        return ResponseEntity(errors, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestExceptions(ex: BadRequestException): ResponseEntity<Map<String, String?>> {
        val errors = mapOf(
            "errorMessage" to ex.localizedMessage,
            "statusCode" to HttpStatus.BAD_REQUEST.name
        )

        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String?>> {
        val errors = ex.bindingResult
            .allErrors
            .associateBy({ (it as FieldError).field }, { it.defaultMessage })

        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationExceptions(ex: ConstraintViolationException): ResponseEntity<Map<String, String?>> {
        val errors = ex.constraintViolations
            .associateBy({ it.propertyPath.toString() }, { it.message })

        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException): ResponseEntity<Map<String, String>> {
        val errorResponse = mapOf(
            "errorMessage" to "데이터 무결성 제약 조건 위반",
            "statusCode" to HttpStatus.BAD_REQUEST.name
        )

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<Map<String, String>> {
        val errorResponse = mapOf(
            "errorMessage" to ex.localizedMessage,
            "statusCode" to HttpStatus.BAD_REQUEST.name
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }
}
