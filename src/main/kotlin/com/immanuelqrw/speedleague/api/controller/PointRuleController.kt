package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.LeaguePointRule as LeaguePointRuleInput
import com.immanuelqrw.speedleague.api.dto.output.PointRule as PointRuleOutput
import com.immanuelqrw.speedleague.api.service.PointService
import com.immanuelqrw.speedleague.api.service.seek.PointRuleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pointRule")
class PointRuleController {

    @Autowired
    private lateinit var pointService: PointService

    @Autowired
    private lateinit var pointRuleService: PointRuleService

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody leaguePointRuleInput: LeaguePointRuleInput): List<PointRuleOutput> {
        return pointService.addPointRules(leaguePointRuleInput).map { pointRule ->
            pointRule.output
        }.sortedBy { it.placement }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<PointRuleOutput> {
        return pointRuleService.findAll(search = search).map { pointRule ->
            pointRule.output
        }.sortedBy { it.placement }
    }

    @GetMapping(path = ["/league/{league}/{season}/{tier}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @PathVariable("league")
        leagueName: String,
        @PathVariable("season")
        season: Int,
        @PathVariable("tier")
        tierLevel: Int
    ): List<PointRuleOutput> {
        return pointRuleService.findAllByLeague(leagueName, season, tierLevel).map { pointRule ->
            pointRule.output
        }.sortedBy { it.placement }
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun replace(@RequestBody leaguePointRuleInput: LeaguePointRuleInput): List<PointRuleOutput> {
        return pointService.replacePointRules(leaguePointRuleInput).map { pointRule ->
            pointRule.output
        }.sortedBy { it.placement }
    }

}
