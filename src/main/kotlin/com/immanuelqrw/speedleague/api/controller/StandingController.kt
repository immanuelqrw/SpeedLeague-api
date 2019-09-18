package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.output.QualifiedRunner
import com.immanuelqrw.speedleague.api.entity.RaceRunner
import com.immanuelqrw.speedleague.api.service.PlayoffService
import com.immanuelqrw.speedleague.api.dto.output.RaceTime as RaceTimeOutput
import com.immanuelqrw.speedleague.api.dto.output.Standing as StandingOutput
import com.immanuelqrw.speedleague.api.service.StandingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/standings")
class StandingController {

    @Autowired
    private lateinit var standingService: StandingService

    @Autowired
    private lateinit var playoffService: PlayoffService

    private fun convertToOutput(raceRunner: RaceRunner): RaceTimeOutput {
        return raceRunner.run {
            RaceTimeOutput(
                raceName = race.name,
                runnerName = runner.name,
                outcome = outcome,
                time = time,
                placement = placement
            )
        }
    }

    @GetMapping(path = ["/generate"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun generateStandings(
        @RequestParam("league")
        leagueName: String,
        @RequestParam("season")
        season: Int,
        @RequestParam("tierName")
        tierName: String,
        @RequestParam("tierLevel")
        tierLevel: Int
    ): Iterable<StandingOutput> {
        return standingService.calculateStandings(leagueName, season, tierLevel)
    }

    @GetMapping(path = ["/qualifiedRunners"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun qualifiedRunner(
        @RequestParam("league")
        leagueName: String,
        @RequestParam("season")
        season: Int,
        @RequestParam("tierLevel")
        tierLevel: Int,
        @RequestParam("top")
        top: Int
    ): Iterable<QualifiedRunner> {
        val standings: List<StandingOutput> = standingService.calculateStandings(leagueName, season, tierLevel)
        return playoffService.matchQualifiedRunners(leagueName, season, tierLevel, top, standings)
    }

    // ? Consider moving to a different controller/service
    @GetMapping(path = ["/calculatePlacements"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun calculatePlacements(
        @RequestParam("race")
        raceName: String
    ): List<RaceTimeOutput> {
        return standingService.calculateRacePlacements(raceName).map { raceRunner -> convertToOutput(raceRunner) }
    }

}
