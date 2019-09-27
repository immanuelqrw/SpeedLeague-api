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

    @GetMapping(path = ["/generate/{league}/{season}/{tier}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun generateStandings(
        @PathVariable("league")
        leagueName: String,
        @PathVariable("season")
        season: Int,
        @PathVariable("tier")
        tierLevel: Int
    ): Iterable<StandingOutput> {
        return standingService.generateStandings(leagueName, season, tierLevel)
    }

    @GetMapping(path = ["/qualifiedRunners/{league}/{season}/{tier}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findQualifiedRunners(
        @PathVariable("league")
        leagueName: String,
        @PathVariable("season")
        season: Int,
        @PathVariable("tier")
        tierLevel: Int,
        @RequestParam("top")
        top: Int = 8
    ): Iterable<QualifiedRunner> {
        return standingService.findQualifiedRunners(leagueName, season, tierLevel, top)
    }

    @GetMapping(path = ["/calculatePlacements/{race}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun calculatePlacements(
        @PathVariable("race")
        raceName: String
    ): List<RaceTimeOutput> {
        return standingService.calculatePlacements(raceName)
    }

}
