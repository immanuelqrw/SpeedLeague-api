package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.Runner as RunnerDTO
import com.immanuelqrw.speedleague.api.entity.Runner
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerService
import com.immanuelqrw.speedleague.api.service.seek.RunnerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/runner")
class RunnerController {

    @Autowired
    private lateinit var runnerService: RunnerService

    @Autowired
    private lateinit var raceRunnerService: RaceRunnerService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody runnerDTO: RunnerDTO): Runner {
        return runnerDTO.run {
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

    @GetMapping(path = ["/race/{raceName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @PathVariable("raceName")
        raceName: String
    ): List<Runner> {
        return raceRunnerService.findByRace(raceName).map { it.runner }
    }

}
