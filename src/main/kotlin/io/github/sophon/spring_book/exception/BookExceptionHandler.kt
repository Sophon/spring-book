package io.github.sophon.spring_book.exception

import io.github.sophon.spring_book.model.BookErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import kotlin.time.Clock

@ControllerAdvice
internal class BookExceptionHandler {

    @ExceptionHandler
    private fun handleException(exception: BookNotFoundException): ResponseEntity<BookErrorResponseDto> {
        val error = BookErrorResponseDto(
            status = HttpStatus.NOT_FOUND.value(),
            message = exception.message.orEmpty(),
            timeStamp = Clock.System.now().toEpochMilliseconds(),
        )
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    private fun handleException(exception: Exception): ResponseEntity<BookErrorResponseDto> {
        val error = BookErrorResponseDto(
            status = HttpStatus.BAD_REQUEST.value(),
//            message = exception.message.orEmpty(),
            message = "Invalid request",
            timeStamp = Clock.System.now().toEpochMilliseconds(),
        )
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }
}
