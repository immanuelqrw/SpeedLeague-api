package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.input.LeaguePointRule
import com.immanuelqrw.speedleague.api.dto.output.QualifiedRunner
import com.immanuelqrw.speedleague.api.dto.output.Standing
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.PointRule
import com.immanuelqrw.speedleague.api.entity.Qualifier
import com.immanuelqrw.speedleague.api.service.seek.LeagueService
import com.immanuelqrw.speedleague.api.service.seek.PointRuleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PointService {
    
    @Autowired
    private lateinit var leagueService: LeagueService

    @Autowired
    private lateinit var pointRuleService: PointRuleService

    private fun attachPointRules(league: League, leagueRule: LeaguePointRule): List<PointRule> {
        return leagueRule.pointRules.map {
            val pointRule = PointRule(
                placement = it.placement,
                amount = it.amount,
                league = league
            )

            pointRuleService.create(pointRule)
        }
    }

    fun addPointRules(leagueRule: LeaguePointRule): List<PointRule> {
        return leagueRule.run {
            val league: League = leagueService.find(leagueName, season, tierName, tierLevel)

            attachPointRules(league, leagueRule)
        }

    }

    fun replacePointRules(leagueRule: LeaguePointRule): List<PointRule> {
        return leagueRule.run {
            val league: League = leagueService.find(leagueName, season, tierName, tierLevel)

            pointRuleService.deleteAll(league.pointRules)

            attachPointRules(league, leagueRule)
        }

    }

}
