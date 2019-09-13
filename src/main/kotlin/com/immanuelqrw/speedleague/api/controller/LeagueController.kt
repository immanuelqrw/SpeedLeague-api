package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.LeaguePlayoffRule
import com.immanuelqrw.speedleague.api.dto.input.League as LeagueDTO
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.service.PlayoffService
import com.immanuelqrw.speedleague.api.service.seek.LeagueService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/league")
class LeagueController {

    @Autowired
    private lateinit var leagueService: LeagueService

    @Autowired
    private lateinit var playoffService: PlayoffService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody leagueDTO: LeagueDTO): League {
        return leagueDTO.run {
            val league = League(
                name = name,
                startedOn = startedOn,
                defaultTime = defaultTime
            )
            val createdLeague: League = leagueService.create(league)

            val leaguePlayoffRule = LeaguePlayoffRule(
                leagueName = name,
                qualifierRules = leagueDTO.qualifierRules
            )

            playoffService.addPlayoffRules(leaguePlayoffRule)

            createdLeague
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<League> {
        return leagueService.findAll(search = search)
    }

}
