package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.input.LeaguePointRule as LeaguePointRuleInput
import com.immanuelqrw.speedleague.api.dto.output.PointRule as PointRuleOutput
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.PointRule
import com.immanuelqrw.speedleague.api.service.seek.LeagueSeekService
import com.immanuelqrw.speedleague.api.service.seek.PointRuleSeekService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PointService {
    
    @Autowired
    private lateinit var leagueSeekService: LeagueSeekService

    @Autowired
    private lateinit var pointRuleSeekService: PointRuleSeekService

    private fun attachRules(league: League, leagueRule: LeaguePointRuleInput): List<PointRule> {
        return leagueRule.pointRules.map {
            val pointRule = PointRule(
                placement = it.placement,
                amount = it.amount,
                league = league
            )

            pointRuleSeekService.create(pointRule)
        }
    }

    fun addRules(leagueRule: LeaguePointRuleInput): List<PointRule> {
        return leagueRule.run {
            val league: League = leagueSeekService.find(leagueName, season, tierLevel)

            // - Add more descriptive error message
            leagueSeekService.validateLeagueChange(league.endedOn)

            attachRules(league, leagueRule)
        }

    }

    private fun replaceRules(leagueRule: LeaguePointRuleInput): List<PointRule> {
        return leagueRule.run {
            val league: League = leagueSeekService.find(leagueName, season, tierLevel)

            // - Add more descriptive error message
            leagueSeekService.validateLeagueChange(league.endedOn)

            pointRuleSeekService.deleteAll(league.pointRules)

            attachRules(league, leagueRule)
        }

    }

    fun create(leaguePointRuleInput: LeaguePointRuleInput): List<PointRuleOutput> {
        return addRules(leaguePointRuleInput).map { pointRule ->
            pointRule.output
        }.sortedBy { it.placement }
    }

    fun findAll(search: String?): Iterable<PointRuleOutput> {
        return pointRuleSeekService.findAll(search = search).map { pointRule ->
            pointRule.output
        }.sortedBy { it.placement }
    }

    fun findAll(leagueName: String, season: Int, tierLevel: Int): List<PointRuleOutput> {
        return pointRuleSeekService.findAllByLeague(leagueName, season, tierLevel).map { pointRule ->
            pointRule.output
        }.sortedBy { it.placement }
    }

    fun replace(leaguePointRuleInput: LeaguePointRuleInput): List<PointRuleOutput> {
        return replaceRules(leaguePointRuleInput).map { pointRule ->
            pointRule.output
        }.sortedBy { it.placement }
    }

}
