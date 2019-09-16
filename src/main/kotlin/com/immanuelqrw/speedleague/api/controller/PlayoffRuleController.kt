package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.LeaguePlayoffRule as LeaguePlayoffRuleInput
import com.immanuelqrw.speedleague.api.dto.output.PlayoffRule as PlayoffRuleOutput
import com.immanuelqrw.speedleague.api.entity.PlayoffRule
import com.immanuelqrw.speedleague.api.service.PlayoffService
import com.immanuelqrw.speedleague.api.service.seek.PlayoffRuleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/playoffRule")
class PlayoffRuleController {

    @Autowired
    private lateinit var playoffService: PlayoffService

    @Autowired
    private lateinit var playoffRuleService: PlayoffRuleService

    private fun convertToOutput(playoffRule: PlayoffRule): PlayoffRuleOutput {
        return playoffRule.run {
            PlayoffRuleOutput(
                qualifier = qualifier,
                count = count,
                leagueName = league.name,
                season = league.season,
                tierLevel = league.tier.level,
                tierName = league.tier.name,
                addedOn = addedOn,
                order = order
            )
        }
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody leaguePlayoffRuleInput: LeaguePlayoffRuleInput): List<PlayoffRuleOutput> {
        return playoffService.addPlayoffRules(leaguePlayoffRuleInput).map { playoffRule ->
            convertToOutput(playoffRule)
        }.sortedBy { it.order }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<PlayoffRuleOutput> {
        return playoffRuleService.findAll(search = search).map { playoffRule ->
            convertToOutput(playoffRule)
        }.sortedBy { it.order }
    }

    @GetMapping(path = ["/league/{leagueName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @PathVariable("leagueName")
        leagueName: String
    ): List<PlayoffRuleOutput> {
        return playoffRuleService.findAllByLeague(leagueName).map { playoffRule ->
            convertToOutput(playoffRule)
        }.sortedBy { it.order }
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun replace(@RequestBody leaguePlayoffRuleInput: LeaguePlayoffRuleInput): List<PlayoffRuleOutput> {
        return playoffService.replacePlayoffRules(leaguePlayoffRuleInput).map { playoffRule ->
            convertToOutput(playoffRule)
        }.sortedBy { it.order }
    }

}
