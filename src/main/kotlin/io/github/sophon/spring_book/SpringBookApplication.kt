package io.github.sophon.spring_book

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBookApplication

fun main(args: Array<String>) {
	runApplication<SpringBookApplication>(*args)
}
