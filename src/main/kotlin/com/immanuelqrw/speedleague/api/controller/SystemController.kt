package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.System as SystemInput
import com.immanuelqrw.speedleague.api.dto.output.System as SystemOutput
import com.immanuelqrw.speedleague.api.entity.System
import com.immanuelqrw.speedleague.api.service.seek.SystemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/system")
class SystemController {

    @Autowired
    private lateinit var systemService: SystemService

    private fun convertToOutput(system: System): SystemOutput {
        return system.run {
            SystemOutput(
                name = name,
                isEmulated = isEmulated
            )
        }
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody systemInput: SystemInput): SystemOutput {
        return systemInput.run {
            val system = System(
                name = name,
                isEmulated = isEmulated
            )
            val createdSystem: System = systemService.create(system)

            convertToOutput(createdSystem)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<SystemOutput> {
        return systemService.findAll(search = search).map { system -> convertToOutput(system) }
    }

}
