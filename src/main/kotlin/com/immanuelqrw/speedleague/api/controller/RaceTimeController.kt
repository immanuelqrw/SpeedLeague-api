package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.entity.Race
import com.immanuelqrw.speedleague.api.entity.RaceRunner
import com.immanuelqrw.speedleague.api.entity.Runner
import com.immanuelqrw.speedleague.api.dto.input.RaceTime as RaceTimeDTO
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerService
import com.immanuelqrw.speedleague.api.service.seek.RaceService
import com.immanuelqrw.speedleague.api.service.seek.RunnerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityNotFoundException

@RestController
@RequestMapping("/raceTime")
class RaceTimeController {

    @Autowired
    private lateinit var raceRunnerService: RaceRunnerService

    @Autowired
    private lateinit var raceService: RaceService

    @Autowired
    private lateinit var runnerService: RunnerService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody entity: RaceTimeDTO): RaceRunner {

        return entity.run {
            val race: Race = raceService.findByName(entity.raceName) ?: throw EntityNotFoundException()
            val runner: Runner = runnerService.findByName(entity.runnerName) ?: throw EntityNotFoundException()

            val raceRunner = RaceRunner(
                race = race,
                runner = runner,
                time = time,
                outcome = outcome
            )
            raceRunnerService.create(raceRunner)
        }
    }

//    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
//    fun findAll(
//        @RequestParam("search")
//        search: String?
//    ): Iterable<RaceTimeDTO> {
//        return raceTimeService.findAll(search = search)
//    }

}
