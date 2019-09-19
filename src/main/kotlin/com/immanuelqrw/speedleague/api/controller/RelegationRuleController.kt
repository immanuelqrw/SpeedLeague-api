package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.LeagueRelegationRule as LeagueRelegationRuleInput
import com.immanuelqrw.speedleague.api.dto.output.RelegationRule as RelegationRuleOutput
import com.immanuelqrw.speedleague.api.entity.RelegationRule
import com.immanuelqrw.speedleague.api.service.RelegationService
import com.immanuelqrw.speedleague.api.service.seek.RelegationRuleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/relegationRule")
class RelegationRuleController {

    @Autowired
    private lateinit var relegationService: RelegationService

    @Autowired
    private lateinit var relegationRuleService: RelegationRuleService

    private fun convertToOutput(relegationRule: RelegationRule): RelegationRuleOutput {
        return relegationRule.run {
            RelegationRuleOutput(
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
    fun create(@RequestBody leagueRelegationRuleInput: LeagueRelegationRuleInput): List<RelegationRuleOutput> {
        return relegationService.addRelegationRules(leagueRelegationRuleInput).map { relegationRule ->
            convertToOutput(relegationRule)
        }.sortedBy { it.order }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<RelegationRuleOutput> {
        return relegationRuleService.findAll(search = search).map { relegationRule ->
            convertToOutput(relegationRule)
        }.sortedBy { it.order }
    }

    @GetMapping(path = ["/league/{league}/{season}/{tier}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @PathVariable("league")
        leagueName: String,
        @PathVariable("season")
        season: Int,
        @PathVariable("tier")
        tierLevel: Int
    ): List<RelegationRuleOutput> {
        return relegationRuleService.findAllByLeague(leagueName, season, tierLevel).map { relegationRule ->
            convertToOutput(relegationRule)
        }.sortedBy { it.order }
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun replace(@RequestBody leagueRelegationRuleInput: LeagueRelegationRuleInput): List<RelegationRuleOutput> {
        return relegationService.replaceRelegationRules(leagueRelegationRuleInput).map { relegationRule ->
            convertToOutput(relegationRule)
        }.sortedBy { it.order }
    }

}
