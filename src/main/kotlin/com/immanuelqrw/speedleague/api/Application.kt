package com.immanuelqrw.speedleague.api

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableAsync


@OpenAPIDefinition(info =
    Info(
        title = "SpeedLeague API",
        version = "0.0.1-pre-alpha",
        description = "Speedrunning League API",
        license = License(name = "MIT", url = "https://choosealicense.com/licenses/mit/"),
        contact = Contact(url = "https://iqrw.dev", name = "IQRW", email = "me@iqrw.dev")
    )
)
@EnableAsync
@EnableCaching
@SpringBootApplication
class Application

fun main() {
    runApplication<Application>()
}
