package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.Runner as RunnerDTO
import com.immanuelqrw.speedleague.api.entity.Runner
import com.immanuelqrw.speedleague.api.service.seek.RunnerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/runner")
class RunnerController {

    @Autowired
    private lateinit var runnerService: RunnerService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody entity: RunnerDTO): Runner {
        return entity.run {
            val runner = Runner(
                name = name
            )
            runnerService.create(runner)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<Runner> {
        return runnerService.findAll(search = search)
    }

}
