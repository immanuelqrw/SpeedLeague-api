package com.immanuelqrw.speedleague.api

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@OpenAPIDefinition(info =
    Info(
        title = "SpeedLeague API",
        version = "0.0.1-pre-alpha",
        description = "Speedrunning League API",
        license = License(name = "MIT", url = "https://choosealicense.com/licenses/mit/"),
        contact = Contact(url = "https://reizu.dev", name = "Reizu", email = "me@reizu.dev")
    )
)
@SpringBootApplication
class Application

fun main() {
    runApplication<Application>()
}
