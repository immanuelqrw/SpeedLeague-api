package com.immanuelqrw.speedleague.api.controller

import com.immanuelqrw.speedleague.api.dto.input.LeaguePlayoffRule as LeaguePlayoffRuleDTO
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

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody leaguePlayoffRuleDTO: LeaguePlayoffRuleDTO): List<PlayoffRule> {
        return playoffService.addPlayoffRules(leaguePlayoffRuleDTO)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
        @RequestParam("search")
        search: String?
    ): Iterable<PlayoffRule> {
        return playoffRuleService.findAll(search = search)
    }

    @PutMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun replace(@RequestBody leaguePlayoffRuleDTO: LeaguePlayoffRuleDTO): List<PlayoffRule> {
        return playoffService.replacePlayoffRules(leaguePlayoffRuleDTO)
    }

}
