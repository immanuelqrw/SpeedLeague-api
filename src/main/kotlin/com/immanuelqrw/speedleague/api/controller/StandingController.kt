package com.immanuelqrw.speedleague.api.controller

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

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("leagueName")
        leagueName: String
    ): Iterable<StandingDTO> {
        return standingService.calculateStandings(leagueName)
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
