package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.Race as RaceInput
import com.immanuelqrw.speedleague.api.dto.output.Race as RaceOutput
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

    private fun convertToOutput(race: Race): RaceOutput {
        return race.run {
            RaceOutput(
                name = name,
                leagueName = league.name,
                startedOn = startedOn
            )
        }
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody raceInput: RaceInput): RaceOutput {
        return raceInput.run {
            val race = Race(
                name = raceName ?: "racename", // ! Replace with name generator
                league = leagueService.findByName(leagueName),
                startedOn = startedOn
            )
            val createdRace: Race = raceService.create(race)

            convertToOutput(createdRace)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<RaceOutput> {
        return raceService.findAll(search = search).map { race -> convertToOutput(race) }
    }

    @GetMapping(path = ["/runner/{runnerName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @PathVariable("runnerName")
        runnerName: String
    ): List<RaceOutput> {
        return raceRunnerService.findAllByRunner(runnerName).map { convertToOutput(it.race) }
    }

}
