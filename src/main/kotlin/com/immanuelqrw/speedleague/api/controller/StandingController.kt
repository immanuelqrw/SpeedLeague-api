package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.output.QualifiedRunner
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

    // ! Convert RequestParam into path variables
    @GetMapping(path = ["/generate"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun generateStandings(
        @RequestParam("league")
        leagueName: String,
        @RequestParam("season")
        season: Int,
        @RequestParam("tier")
        tierLevel: Int
    ): Iterable<StandingOutput> {
        return standingService.generateStandings(leagueName, season, tierLevel)
    }

    @GetMapping(path = ["/qualifiedRunners"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findQualifiedRunners(
        @RequestParam("league")
        leagueName: String,
        @RequestParam("season")
        season: Int,
        @RequestParam("tier")
        tierLevel: Int,
        @RequestParam("top")
        top: Int
    ): Iterable<QualifiedRunner> {
        return standingService.findQualifiedRunners(leagueName, season, tierLevel, top)
    }

    @GetMapping(path = ["/calculatePlacements"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun calculatePlacements(
        @RequestParam("race")
        raceName: String
    ): List<RaceTimeOutput> {
        return standingService.calculatePlacements(raceName)
    }

}
