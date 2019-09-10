package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.output.QualifiedRunner
import com.immanuelqrw.speedleague.api.service.PlayoffService
import com.immanuelqrw.speedleague.api.dto.output.RaceResult as RaceResultDTO
import com.immanuelqrw.speedleague.api.dto.output.Standing as StandingDTO
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

    @GetMapping(path = ["/generate"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun generateStandings(
        @RequestParam("leagueName")
        leagueName: String
    ): Iterable<StandingDTO> {
        return standingService.calculateStandings(leagueName)
    }

    @GetMapping(path = ["/qualifiedRunners"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun qualifiedRunner(
        @RequestParam("leagueName")
        leagueName: String,
        @RequestParam("top")
        top: Int
    ): Iterable<QualifiedRunner> {
        val standings: List<StandingDTO> = standingService.calculateStandings(leagueName)
        return playoffService.matchQualifiedRunners(leagueName, top, standings)
    }

    // ? Consider moving to a different controller/service
    @GetMapping(path = ["/calculatePlacements"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun calculatePlacements(
        @RequestParam("raceName")
        raceName: String
    ): List<RaceResultDTO> {
        return standingService.calculateRacePlacements(raceName)
    }

}
