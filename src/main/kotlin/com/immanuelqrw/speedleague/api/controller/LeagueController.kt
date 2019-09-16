package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.LeaguePlayoffRule
import com.immanuelqrw.speedleague.api.dto.input.League as LeagueInput
import com.immanuelqrw.speedleague.api.dto.output.League as LeagueOutput
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

    private fun convertToOutput(league: League): LeagueOutput {
        return league.run {
            LeagueOutput(
                name = name,
                type = type,
                startedOn = startedOn,
                defaultTime = defaultTime,
                defaultPoints = defaultPoints
            )
        }
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody leagueInput: LeagueInput): LeagueOutput {
        return leagueInput.run {
            val league = League(
                name = name,
                type = type,
                startedOn = startedOn,
                defaultTime = defaultTime,
                defaultPoints = defaultPoints
            )
            val createdLeague: League = leagueService.create(league)

            val leaguePlayoffRule = LeaguePlayoffRule(
                leagueName = name,
                qualifierRules = leagueInput.qualifierRules
            )

            playoffService.addPlayoffRules(leaguePlayoffRule)

            convertToOutput(createdLeague)
        }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<LeagueOutput> {
        return leagueService.findAll(search = search).map { league -> convertToOutput(league) }
    }

}
