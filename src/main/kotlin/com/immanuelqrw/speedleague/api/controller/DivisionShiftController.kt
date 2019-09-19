package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.entity.DivisionShiftRule
import com.immanuelqrw.speedleague.api.dto.input.LeagueDivisionShiftRule as LeagueDivisionShiftRuleInput
import com.immanuelqrw.speedleague.api.dto.output.DivisionShiftRule as DivisionShiftRuleOutput
import com.immanuelqrw.speedleague.api.service.DivisionShiftService
import com.immanuelqrw.speedleague.api.service.seek.DivisionShiftRuleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/divisionShiftRule")
class DivisionShiftController {

    @Autowired
    private lateinit var divisionShiftService: DivisionShiftService

    @Autowired
    private lateinit var divisionShiftRuleService: DivisionShiftRuleService

    private fun DivisionShiftRule.convertToOutput(): DivisionShiftRuleOutput {
        return DivisionShiftRuleOutput(
            qualifier = qualifier,
            count = count,
            shift = shift,
            leagueName = league.name,
            season = league.season,
            tierLevel = league.tier.level,
            tierName = league.tier.name,
            addedOn = addedOn,
            order = order
        )
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return divisionShiftService.addDivisionShiftRules(leagueDivisionShiftRuleInput).map { divisionShiftRule ->
            divisionShiftRule.convertToOutput()
        }.sortedBy { it.order }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<DivisionShiftRuleOutput> {
        return divisionShiftRuleService.findAll(search = search).map { divisionShiftRule ->
            divisionShiftRule.convertToOutput()
        }.sortedBy { it.order }
    }

    @GetMapping(path = ["/divisionShift/{league}/{season}/{tier}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @PathVariable("league")
        leagueName: String,
        @PathVariable("season")
        season: Int,
        @PathVariable("tier")
        tierLevel: Int
    ): List<DivisionShiftRuleOutput> {
        return divisionShiftRuleService.findAllByLeague(leagueName, season, tierLevel).map { divisionShiftRule ->
            divisionShiftRule.convertToOutput()
        }.sortedBy { it.order }
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun replace(@RequestBody leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return divisionShiftService.replaceDivisionShiftRules(leagueDivisionShiftRuleInput).map { divisionShiftRule ->
            divisionShiftRule.convertToOutput()
        }.sortedBy { it.order }
    }

    @PostMapping(path = ["/promotion"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPromotionRules(@RequestBody leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return divisionShiftService.addDivisionShiftRules(leagueDivisionShiftRuleInput).map { promotionRule ->
            promotionRule.convertToOutput()
        }.sortedBy { it.order }
    }

    @GetMapping(path = ["/promotion/{league}/{season}/{tier}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllPromotionRules(
        @PathVariable("league")
        leagueName: String,
        @PathVariable("season")
        season: Int,
        @PathVariable("tier")
        tierLevel: Int
    ): List<DivisionShiftRuleOutput> {
        return divisionShiftRuleService.findAllPromotionByLeague(leagueName, season, tierLevel).map { promotionRule ->
            promotionRule.convertToOutput()
        }.sortedBy { it.order }
    }

    @PutMapping(path = ["/promotion"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun replacePromotionRules(@RequestBody leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return divisionShiftService.replacePromotionRules(leagueDivisionShiftRuleInput).map { promotionRule ->
            promotionRule.convertToOutput()
        }.sortedBy { it.order }
    }

    @PostMapping(path = ["/relegation"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createRelegationRules(@RequestBody leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return divisionShiftService.addDivisionShiftRules(leagueDivisionShiftRuleInput).map { relegationRule ->
            relegationRule.convertToOutput()
        }.sortedBy { it.order }
    }

    @GetMapping(path = ["/relegation/{league}/{season}/{tier}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAllRelegationRules(
        @PathVariable("league")
        leagueName: String,
        @PathVariable("season")
        season: Int,
        @PathVariable("tier")
        tierLevel: Int
    ): List<DivisionShiftRuleOutput> {
        return divisionShiftRuleService.findAllRelegationByLeague(leagueName, season, tierLevel).map { relegationRule ->
            relegationRule.convertToOutput()
        }.sortedBy { it.order }
    }

    @PutMapping(path = ["/relegation"], produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun replaceRelegationRules(@RequestBody leagueDivisionShiftRuleInput: LeagueDivisionShiftRuleInput): List<DivisionShiftRuleOutput> {
        return divisionShiftService.replaceRelegationRules(leagueDivisionShiftRuleInput).map { relegationRule ->
            relegationRule.convertToOutput()
        }.sortedBy { it.order }
    }

}
