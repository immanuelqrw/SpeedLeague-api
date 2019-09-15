package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.LeaguePointRule as LeaguePointRuleInput
import com.immanuelqrw.speedleague.api.dto.output.PointRule as PointRuleOutput
import com.immanuelqrw.speedleague.api.entity.PointRule
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

    private fun convertToOutput(pointRule: PointRule): PointRuleOutput {
        return pointRule.run {
            PointRuleOutput(
                placement = placement,
                amount = amount,
                leagueName = league.name,
                addedOn = addedOn
            )
        }
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody leaguePointRuleInput: LeaguePointRuleInput): List<PointRuleOutput> {
        return pointService.addPointRules(leaguePointRuleInput).map { pointRule ->
            convertToOutput(pointRule)
        }.sortedBy { it.placement }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<PointRuleOutput> {
        return pointRuleService.findAll(search = search).map { pointRule ->
            convertToOutput(pointRule)
        }.sortedBy { it.placement }
    }

    @GetMapping(path = ["/league/{leagueName}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @PathVariable("leagueName")
        leagueName: String
    ): List<PointRuleOutput> {
        return pointRuleService.findByLeague(leagueName).map { pointRule ->
            convertToOutput(pointRule)
        }.sortedBy { it.placement }
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun replace(@RequestBody leaguePointRuleInput: LeaguePointRuleInput): List<PointRuleOutput> {
        return pointService.replacePointRules(leaguePointRuleInput).map { pointRule ->
            convertToOutput(pointRule)
        }.sortedBy { it.placement }
    }

}
