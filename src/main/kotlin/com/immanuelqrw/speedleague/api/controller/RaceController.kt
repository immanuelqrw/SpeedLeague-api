package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.dto.input.Race as RaceInput
import com.immanuelqrw.speedleague.api.dto.output.Race as RaceOutput
import com.immanuelqrw.speedleague.api.entity.Race
import com.immanuelqrw.speedleague.api.exception.LeagueHasEndedException
import com.immanuelqrw.speedleague.api.service.seek.LeagueService
import com.immanuelqrw.speedleague.api.service.seek.RaceRunnerService
import com.immanuelqrw.speedleague.api.service.seek.RaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

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
    fun create(@RequestBody raceInput: RaceInput): RaceOutput {
        return raceInput.run {
            val league: League = leagueService.find(leagueName, season, tierLevel)

            val currentTime: LocalDateTime = LocalDateTime.now()
            league.endedOn?.let {
                if (currentTime >= it) {
                    throw LeagueHasEndedException("Race $raceName cannot be created due to league being over [Ended on $it]")
                }
            }

            // If League has ended, disallow races
            val race = Race(
                name = raceName ?: "racename", // ! Replace with name generator
                league = league,
                startedOn = startedOn
            )
            val createdRace: Race = raceService.create(race)

            createdRace.output
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<RaceOutput> {
        return raceService.findAll(search = search).map { race ->
            race.output
        }
    }

    @GetMapping(path = ["/runner/{runnerName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllRaces(
        @PathVariable("runnerName")
        runnerName: String
    ): List<RaceOutput> {
        return raceRunnerService.findAllByRunner(runnerName).map { raceRunner ->
            raceRunner.race.output
        }
    }

}
