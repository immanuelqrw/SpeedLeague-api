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

    private fun convertToOutput(race: Race, season: Int, tierName: String, tierLevel: Int): RaceOutput {
        return race.run {
            RaceOutput(
                name = name,
                leagueName = league.name,
                startedOn = startedOn,
                season = season,
                tierLevel = tierLevel,
                tierName = tierName
            )
        }
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody raceInput: RaceInput): RaceOutput {
        return raceInput.run {
            val race = Race(
                name = raceName ?: "racename", // ! Replace with name generator
                league = leagueService.find(leagueName, season, tierName, tierLevel),
                startedOn = startedOn
            )
            val createdRace: Race = raceService.create(race)

            convertToOutput(createdRace, season, tierName, tierLevel)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<RaceOutput> {
        return raceService.findAll(search = search).map { race ->
            convertToOutput(race, race.league.season, race.league.tier.name, race.league.tier.level)
        }
    }

    @GetMapping(path = ["/runner/{runnerName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllRaces(
        @PathVariable("runnerName")
        runnerName: String
    ): List<RaceOutput> {
        return raceRunnerService.findAllByRunner(runnerName).map { raceRunner ->
            convertToOutput(raceRunner.race, raceRunner.race.league.season, raceRunner.race.league.tier.name, raceRunner.race.league.tier.level)
        }
    }

}
