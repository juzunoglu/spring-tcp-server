package com.jollify.chat.social

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info = Info(title = "APIs", version = "1.0", description = "TCP Server OHM APIs v1.0"))
class SocialApplication
fun main(args: Array<String>) {
	runApplication<SocialApplication>(*args)
}
