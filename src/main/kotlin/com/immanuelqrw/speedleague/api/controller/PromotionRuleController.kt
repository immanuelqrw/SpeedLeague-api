package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.LeaguePromotionRule as LeaguePromotionRuleInput
import com.immanuelqrw.speedleague.api.dto.output.PromotionRule as PromotionRuleOutput
import com.immanuelqrw.speedleague.api.entity.PromotionRule
import com.immanuelqrw.speedleague.api.service.PromotionService
import com.immanuelqrw.speedleague.api.service.seek.PromotionRuleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/promotionRule")
class PromotionRuleController {

    @Autowired
    private lateinit var promotionService: PromotionService

    @Autowired
    private lateinit var promotionRuleService: PromotionRuleService

    private fun convertToOutput(promotionRule: PromotionRule): PromotionRuleOutput {
        return promotionRule.run {
            PromotionRuleOutput(
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
    fun create(@RequestBody leaguePromotionRuleInput: LeaguePromotionRuleInput): List<PromotionRuleOutput> {
        return promotionService.addPromotionRules(leaguePromotionRuleInput).map { promotionRule ->
            convertToOutput(promotionRule)
        }.sortedBy { it.order }
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<PromotionRuleOutput> {
        return promotionRuleService.findAll(search = search).map { promotionRule ->
            convertToOutput(promotionRule)
        }.sortedBy { it.order }
    }

    @GetMapping(path = ["/league/{leagueName}/{season}/{tierLevel}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @PathVariable("leagueName")
        leagueName: String,
        @PathVariable("season")
        season: Int,
        @PathVariable("tierLevel")
        tierLevel: Int
    ): List<PromotionRuleOutput> {
        return promotionRuleService.findAllByLeague(leagueName, season, tierLevel).map { promotionRule ->
            convertToOutput(promotionRule)
        }.sortedBy { it.order }
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun replace(@RequestBody leaguePromotionRuleInput: LeaguePromotionRuleInput): List<PromotionRuleOutput> {
        return promotionService.replacePromotionRules(leaguePromotionRuleInput).map { promotionRule ->
            convertToOutput(promotionRule)
        }.sortedBy { it.order }
    }

}
