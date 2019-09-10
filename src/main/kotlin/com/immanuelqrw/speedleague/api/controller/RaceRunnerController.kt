package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.core.entity.UniqueEntityable
import com.immanuelqrw.speedleague.api.entity.Race
import com.immanuelqrw.speedleague.api.entity.Runner
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/raceRunner")
class RaceRunnerController {

    @Autowired
    private lateinit var raceRunnerService: RaceRunnerService

    // ! Look into better way of fixing this
    // ! Consider richer output
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("runnerName")
        runnerName: String? = null,
        @RequestParam("raceName")
        raceName: String? = null
    ): Iterable<UniqueEntityable> {
        val races: List<Race>? = runnerName?.run {
            raceRunnerService.findByRunner(runnerName).map { raceRunner ->
                raceRunner.race
            }
        }

        val runners: List<Runner>? = raceName?.run {
            raceRunnerService.findByRace(raceName).map { raceRunner ->
                raceRunner.runner
            }
        }

        return races ?: runners ?: emptyList()
    }

}
