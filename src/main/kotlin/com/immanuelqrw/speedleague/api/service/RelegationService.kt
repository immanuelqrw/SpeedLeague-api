package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.input.LeagueRelegationRule
import com.immanuelqrw.speedleague.api.dto.output.QualifiedRunner
import com.immanuelqrw.speedleague.api.dto.output.Standing
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.RelegationRule
import com.immanuelqrw.speedleague.api.entity.Qualifier
import com.immanuelqrw.speedleague.api.service.seek.LeagueService
import com.immanuelqrw.speedleague.api.service.seek.RelegationRuleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import sun.jvm.hotspot.oops.CellTypeState.top

@Service
class RelegationService {
    
    @Autowired
    private lateinit var leagueService: LeagueService

    @Autowired
    private lateinit var relegationRuleService: RelegationRuleService

    private fun attachRelegationRules(league: League, leagueRule: LeagueRelegationRule): List<RelegationRule> {
        return leagueRule.qualifierRules.mapIndexed { index, qualifierRule ->
            val relegationRule = RelegationRule(
                qualifier = qualifierRule.qualifier,
                count = qualifierRule.count,
                league = league,
                order = index + 1
            )

            relegationRuleService.create(relegationRule)
        }
    }

    fun addRelegationRules(leagueRule: LeagueRelegationRule): List<RelegationRule> {
        return leagueRule.run {
            val league: League = leagueService.find(leagueName, season, tierLevel)

            attachRelegationRules(league, leagueRule)
        }
    }

    fun replaceRelegationRules(leagueRule: LeagueRelegationRule): List<RelegationRule> {
        return leagueRule.run {
            val league: League = leagueService.find(leagueName, season, tierLevel)

            relegationRuleService.deleteAll(league.relegationRules)

            attachRelegationRules(league, leagueRule)
        }
    }

    private fun findTopRunners(top: Int, standings: List<Standing>): MutableMap<Qualifier, List<QualifiedRunner>> {
        return mutableMapOf(
            Qualifier.POINTS to standings.sortedBy { it.points }.take(top),
            Qualifier.POINTS_PER_RACE to standings.sortedBy { it.pointsPerRace }.take(top),
            Qualifier.WINS to standings.sortedBy { it.wins }.take(top),
            Qualifier.AVERAGE_TIME to standings.sortedBy { it.averageTime }.take(top)
        )
    }

    fun matchQualifiedRunners(leagueName: String, season: Int, tierLevel: Int, standings: List<Standing>): List<QualifiedRunner> {
        val league: League = leagueService.find(leagueName, season, tierLevel)

        val top: Int = league.relegations
        val topRunners: MutableMap<Qualifier, List<QualifiedRunner>> = findTopRunners(top, standings)

        val qualifiedRunners: LinkedHashSet<QualifiedRunner> = linkedSetOf()

        // ! There might be an issue with infinite loop if less runners than top
        val orderedRelegationRules: List<RelegationRule> = league.relegationRules.sortedBy { it.order }
        while (qualifiedRunners.size < top) {
            orderedRelegationRules.forEach { relegationRule ->
                val qualifier: Qualifier = relegationRule.qualifier
                val possiblyQualifiedRunners: List<QualifiedRunner> = topRunners[qualifier]
                    ?: throw IllegalStateException("Each Qualifier should have a list of possible runners")
                // - Consider outputting each qualification method
                val orderedQualifiedRunners = possiblyQualifiedRunners.take(relegationRule.count)

                qualifiedRunners.addAll(orderedQualifiedRunners)
                topRunners[qualifier] = possiblyQualifiedRunners.drop(relegationRule.count)
            }
        }

        return qualifiedRunners.take(top)

    }

}
