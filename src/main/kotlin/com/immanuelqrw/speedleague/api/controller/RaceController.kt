package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.Race as RaceDTO
import com.immanuelqrw.speedleague.api.entity.Race
import com.immanuelqrw.speedleague.api.service.seek.LeagueService
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerService
import com.immanuelqrw.speedleague.api.service.seek.RaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/race")
class RaceController {

    @Autowired
    private lateinit var raceService: RaceService

    @Autowired
    private lateinit var leagueService: LeagueService

    @Autowired
    private lateinit var raceRunnerService: RaceRunnerService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody raceDTO: RaceDTO): Race {
        return raceDTO.run {
            val race = Race(
                name = raceName ?: "racename", // ! Replace with name generator
                league = leagueService.findByName(leagueName),
                startedOn = startedOn
            )
            raceService.create(race)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<Race> {
        return raceService.findAll(search = search)
    }

    @GetMapping(path = ["/runner/{runnerName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @PathVariable("runnerName")
        runnerName: String
    ): List<Race> {
        return raceRunnerService.findByRunner(runnerName).map { it.race }
    }

}
