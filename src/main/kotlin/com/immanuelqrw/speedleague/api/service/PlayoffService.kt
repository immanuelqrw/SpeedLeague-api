package com.immanuelqrw.speedleague.api.service

import com.immanuelqrw.speedleague.api.dto.input.LeaguePlayoffRule
import com.immanuelqrw.speedleague.api.dto.output.QualifiedRunner
import com.immanuelqrw.speedleague.api.dto.output.Standing
import com.immanuelqrw.speedleague.api.entity.League
import com.immanuelqrw.speedleague.api.entity.PlayoffRule
import com.immanuelqrw.speedleague.api.entity.Qualifier
import com.immanuelqrw.speedleague.api.service.seek.LeagueService
import com.immanuelqrw.speedleague.api.service.seek.PlayoffRuleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PlayoffService {
    
    @Autowired
    private lateinit var leagueService: LeagueService

    @Autowired
    private lateinit var playoffRuleService: PlayoffRuleService

    private fun attachPlayoffRules(league: League, leagueRule: LeaguePlayoffRule): List<PlayoffRule> {
        return leagueRule.qualifierRules.mapIndexed { index, qualifierRule ->
            val playoffRule = PlayoffRule(
                qualifier = qualifierRule.qualifier,
                count = qualifierRule.count,
                league = league,
                order = index + 1
            )

            playoffRuleService.create(playoffRule)
        }
    }

    fun addPlayoffRules(leagueRule: LeaguePlayoffRule): List<PlayoffRule> {
        return leagueRule.run {
            val league: League = leagueService.find(leagueName, season, tierLevel)

            attachPlayoffRules(league, leagueRule)
        }
    }

    fun replacePlayoffRules(leagueRule: LeaguePlayoffRule): List<PlayoffRule> {
        return leagueRule.run {
            val league: League = leagueService.find(leagueName, season, tierLevel)

            playoffRuleService.deleteAll(league.playoffRules)

            attachPlayoffRules(league, leagueRule)
        }
    }

    private fun findTopRunners(top: Int, standings: List<Standing>): MutableMap<Qualifier, List<QualifiedRunner>> {
        return mutableMapOf(
            Qualifier.POINTS to standings.sortedByDescending { it.points }.take(top),
            Qualifier.POINTS_PER_RACE to standings.sortedByDescending { it.pointsPerRace }.take(top),
            Qualifier.WINS to standings.sortedByDescending { it.wins }.take(top),
            Qualifier.AVERAGE_TIME to standings.sortedByDescending { it.averageTime }.take(top)
        )
    }

    fun matchQualifiedRunners(leagueName: String, season: Int, tierName: String, tierLevel: Int, top: Int, standings: List<Standing>): List<QualifiedRunner> {
        val topRunners: MutableMap<Qualifier, List<QualifiedRunner>> = findTopRunners(top, standings)

        val league: League = leagueService.find(leagueName, season, tierLevel)

        val qualifiedRunners: LinkedHashSet<QualifiedRunner> = linkedSetOf()

        // ! There might be an issue with infinite loop if less runners than top
        val orderedPlayoffRules: List<PlayoffRule> = league.playoffRules.sortedBy { it.order }
        while (qualifiedRunners.size < top) {
            orderedPlayoffRules.forEach { playoffRule ->
                val qualifier: Qualifier = playoffRule.qualifier
                val possiblyQualifiedRunners: List<QualifiedRunner> = topRunners[qualifier]
                    ?: throw IllegalStateException("Each Qualifier should have a list of possible runners")
                // - Consider outputting each qualification method
                val orderedQualifiedRunners = possiblyQualifiedRunners.take(playoffRule.count)

                qualifiedRunners.addAll(orderedQualifiedRunners)
                topRunners[qualifier] = possiblyQualifiedRunners.drop(playoffRule.count)
            }
        }

        return qualifiedRunners.take(top)

    }

}
